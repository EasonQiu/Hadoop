hadoop fs -rm -r /output/wc
hadoop jar /home/hadoop/lib/hadoop-train-1.0.jar com.imooc.hadoop.mapreduce.WordCountApp hdfs://hadoop000:8020/hello.txt hdfs://hadoop000:8020/output/wc
