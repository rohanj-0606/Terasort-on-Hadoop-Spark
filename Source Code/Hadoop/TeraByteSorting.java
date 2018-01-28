import java.io.IOException;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.GenericOptionsParser;

public class TeraByteSorting {

        public static class Map extends Mapper<Text, Text, Text, Text> {

                private Text map_key = new Text();
                private Text map_value = new Text();

                public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

                String temp = value.toString();
                	String mapK = temp.substring(0, 10);
                	//Splitting the input record in key of first 10 bytes and remaining bytes for value 
                	String mapV = temp.substring(13, 98);
                	mapV = mapV + temp.substring(temp.length() - 1);
                map_key.set(mapK);
                map_value.set(mapV);
        context.write(map_key,map_value );
        // Key and value emitted by Map Task
                }
        }
        
        // Map Function 

        public static class Reduce extends Reducer<Text, Text, Text, Text> {

                public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
                	for (Text val : values)           
                	// Iterating over values	
                	context.write(key, val);
                	//Key value emitted by Reducer Task
                }
        }// Reduce Function

        public static void main(String[] args) throws Exception {
                Configuration conf = new Configuration();
                String[] args1 = new GenericOptionsParser(conf, args).getRemainingArgs();
                if (args1.length != 3) {
                        System.out.println("Please Enter Proper arguments");
                        System.exit(2);
                }

                	Job job = new Job(conf, "TeraByteSorting");
                	//creating Job for running Map Reduce Program
                	job.setJarByClass(TeraByteSorting.class);
                	job.setMapperClass(Map.class);
                	// Setting Map Class
                	job.setReducerClass(Reduce.class);
                	//Setting Reduce Class
                	job.setNumReduceTasks(32);
                	// Setting the number of reducers
                job.setInputFormatClass(KeyValueTextInputFormat.class);
                //Setting the input format class
                job.setMapOutputKeyClass(Text.class);
                // Setting output key class for Map
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(Text.class);
                // Setting the out put key class for map and reduce task
                FileInputFormat.addInputPath(job, new Path(args1[0]));
               //Obtaining the path for input file
                Path Directory = new Path(args1[2]);
                Path partition_path = new Path(Directory, "partitioning");
                //Directory for storing intermediate partition file
                TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), partition_path);
                // Setting the path for storing intermediate partition file created by TotalOrderPartitioner Class
                InputSampler.Sampler<Text,Text> sampler = new InputSampler.RandomSampler<>(0.01,1000,100);
                // Creating input sampler
                InputSampler.writePartitionFile(job, sampler);
                //Creating intermediate sampler
                job.setPartitionerClass(TotalOrderPartitioner.class);
                //Using hadoop's internal  TotalOrderPartitioner class
                FileOutputFormat.setOutputPath(job, new Path(args1[1]));
                //Obtaining the path for output file
                System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
}
