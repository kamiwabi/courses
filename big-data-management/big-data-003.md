# Instalasi dan Konfigurasi Apache Hadoop

## Persiapan

Untuk keperluan instalasi, siapkan direktori baru yang kosong. Kita akan menggunakan direktori ini sebagai dasar dari semua software yang kita gunakan:

```bash
$ mkdir -p $HOME/software/bigdata
$ mkdir -p $HOME/master
$ mkdir -p $HOME/env
$ mkdir -p $HOME/training
```

* Baris 1 digunakan sebagai tempat instalasi semua software yang digunakan
* Baris 2 digunakan untuu menyimpan semua master software
* Baris 3 digunakan untuk menyimpan semua file-file yang mengatur *environment variables*
* Baris 4 digunakan untuk menyimpan semua file yang dibuat saat training.

**Catatan:** Perintah-perintah yang dikerjakan di shell / CLI dilakukan pada kondisi keaktifan user tertentu. Jika diawali dengan **$**, maka perintah tersebut dikerjakan oleh user biasa. Jika diawali dengan **#**, maka perintah tersebut dikerjakan oleh *superuser* / *root*.

Setelah membuat direktori, masukkan semua file master di direktori `$HOME/master` kemudian masuk ke direktori tempat software akan di-install:

```bash
$ cd $HOME/software/bigdata
```

Pada posisi ini, semua direktori sudah tersedia dan semua software yang dibutuhkan sudah berada di `$HOME/master`. 

## Install Software yang Diperlukan

Ada 2 software yang harus diinstall untuk keperluan Hadoop ini. Gunakan perintah berikut untuk
instalasi:

```bash
$ sudo apt install ssh pdsh
```

## Install JDK

```bash
$ tar -xvf ~/master/jdk-8u241-linux-x64.tar.gz 
...
...
...
...
jdk1.8.0_241/include/jawt.h
jdk1.8.0_241/include/classfile_constants.h
jdk1.8.0_241/include/linux/
jdk1.8.0_241/include/linux/jni_md.h
jdk1.8.0_241/include/linux/jawt_md.h
jdk1.8.0_241/include/jdwpTransport.h
jdk1.8.0_241/include/jvmti.h
jdk1.8.0_241/include/jvmticmlr.h
$
```

Setelah itu, atur *environment variables* dengan membuat suatu file di `$HOME/env`:

```bash
export JAVA_HOME=$HOME/software/bigdata/jdk1.8.0_241

export PATH=$JAVA_HOME/bin:$PATH
export MANPATH=$JAVA_HOME/man:$MANPATH

export LD_LIBRARY_PATH=$JAVA_HOME/jre/lib/amd64:$JAVA_HOME/jre/lib/amd64/jli:$JAVA_HOME/jre/lib/amd64/server:$LD_LIBRARY_PATH
```

Simpan dengan nama file `jdk8`. Setelah itu uji instalasi ini:

```bash
$ java -version
java version "1.8.0_241"
Java(TM) SE Runtime Environment (build 1.8.0_241-b07)
Java HotSpot(TM) 64-Bit Server VM (build 25.241-b07, mixed mode)
$ javac -version
javac 1.8.0_241
$ 
```

## Install Apache Hadoop

Seperti halnya instalasi JDK, instalasi Hadoop dilakukan dengan mengekstrak kemudian mengatur
*environment variables~.

```
$ tar -xvf ~/master/hadoop-3.2.1.tar.g
...
...
...
...
...
hadoop-3.2.1/sbin/stop-balancer.sh
hadoop-3.2.1/sbin/workers.sh
hadoop-3.2.1/sbin/start-balancer.sh
hadoop-3.2.1/sbin/start-all.cmd
hadoop-3.2.1/sbin/hadoop-daemon.sh
hadoop-3.2.1/sbin/stop-yarn.sh
hadoop-3.2.1/sbin/start-all.sh
$ 
```

Setelah itu, buat file yang berisi *environment variables* dengan nama `$HOME/env/hadoop` sebagai
berikut:

```bash
export PATH=$HOME/software/bigdata/hadoop-3.2.1/bin:$PATH
```

Setelah itu, uji instalasi dengan perintah berikut ini:

```bash
$ source env/hadoop
```
Setelah itu, uji hasil instalasi:

```
$ hadoop
Usage: hadoop [OPTIONS] SUBCOMMAND [SUBCOMMAND OPTIONS]
 or    hadoop [OPTIONS] CLASSNAME [CLASSNAME OPTIONS]
  where CLASSNAME is a user-provided Java class

  OPTIONS is none or any of:

buildpaths                       attempt to add class files from build tree
--config dir                     Hadoop config directory
--debug                          turn on shell script debug mode
--help                           usage information
hostnames list[,of,host,names]   hosts to use in slave mode
hosts filename                   list of hosts to use in slave mode
loglevel level                   set the log4j level for this command
workers                          turn on worker mode

  SUBCOMMAND is one of:


    Admin Commands:

daemonlog     get/set the log level for each daemon

    Client Commands:

archive       create a Hadoop archive
checknative   check native Hadoop and compression libraries availability
classpath     prints the class path needed to get the Hadoop jar and the required libraries
conftest      validate configuration XML files
credential    interact with credential providers
distch        distributed metadata changer
distcp        copy file or directories recursively
dtutil        operations related to delegation tokens
envvars       display computed Hadoop environment variables
fs            run a generic filesystem user client
gridmix       submit a mix of synthetic job, modeling a profiled from production load
jar <jar>     run a jar file. NOTE: please use "yarn jar" to launch YARN applications, not this command.
jnipath       prints the java.library.path
kdiag         Diagnose Kerberos Problems
kerbname      show auth_to_local principal conversion
key           manage keys via the KeyProvider
rumenfolder   scale a rumen input trace
rumentrace    convert logs into a rumen trace
s3guard       manage metadata on S3
trace         view and modify Hadoop tracing settings
version       print the version

    Daemon Commands:

kms           run KMS, the Key Management Server

SUBCOMMAND may print help when invoked w/o parameters or with -h.
```

JIka berhasil sampai disini, berarti instalasi sudah dilaksanakan dengan baik.

## Mode Operasi Hadoop

Hadoop bisa dijelankan dalam 3 mode:

1.  *Standalone*
2.  *Pseudo-Distributed*
3.  *Fully-Distributed*

### Standalone

Pada mode ini, Hadoop biasanya hanya digunakan untuk proses kecil dan untuk keperluan eksperimen serta *debugging* saja. Dengan menggunakan mode ini, Hadoop (yang dibuat menggunakan Java) akan dijalankan dalam 1 proses Java saja.

Untuk menguji mode ini, kita akan menggunakan contoh yang sudah dibuat oleh Hadoop untuk MapReduce (ada di file `$HOME/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar` untuk proses grep (mengambil teks sesuai dengan pola tertentu). Semua aktivitas di Standalone ini dikerjakan di `$HOME/training/001-standalone`, buat direktorinya jika belum ada:

```bash
$ mkdir $HOME/training/001-standalone
```
Kerjakan perintah-perintah berikut ini:

```bash
$ cd $HOME/training/001-standalone
$ mkdir input
$ cp $HOME/software/bigdata/hadoop-3.2.1/etc/hadoop/*.xml input/
$ ls -la input/
total 60
drwxr-xr-x  2 zaky zaky  4096 Mar  5 23:03 .
drwxr-xr-x 11 zaky zaky  4096 Mar  5 23:02 ..
-rw-r--r--  1 zaky zaky  8260 Mar  5 23:03 capacity-scheduler.xml
-rw-r--r--  1 zaky zaky   774 Mar  5 23:03 core-site.xml
-rw-r--r--  1 zaky zaky 11392 Mar  5 23:03 hadoop-policy.xml
-rw-r--r--  1 zaky zaky   775 Mar  5 23:03 hdfs-site.xml
-rw-r--r--  1 zaky zaky   620 Mar  5 23:03 httpfs-site.xml
-rw-r--r--  1 zaky zaky  3518 Mar  5 23:03 kms-acls.xml
-rw-r--r--  1 zaky zaky   682 Mar  5 23:03 kms-site.xml
-rw-r--r--  1 zaky zaky   758 Mar  5 23:03 mapred-site.xml
-rw-r--r--  1 zaky zaky   690 Mar  5 23:03 yarn-site.xml
$ 
```

Kerjakan proses MapReduce standalone berikut:

```bash
$ hadoop jar  ~/software/bigdata/hadoop-3.2.1/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar grep input output 'dfs[a-z.]+'
2020-03-05 23:03:48,272 INFO impl.MetricsConfig: Loaded properties from hadoop-metrics2.properties
2020-03-05 23:03:48,320 INFO impl.MetricsSystemImpl: Scheduled Metric snapshot period at 10 second(s).
2020-03-05 23:03:48,321 INFO impl.MetricsSystemImpl: JobTracker metrics system started
2020-03-05 23:03:48,534 INFO input.FileInputFormat: Total input files to process : 9
2020-03-05 23:03:48,551 INFO mapreduce.JobSubmitter: number of splits:9
2020-03-05 23:03:48,638 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local2085274023_0001
2020-03-05 23:03:48,638 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-05 23:03:48,734 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-05 23:03:48,735 INFO mapreduce.Job: Running job: job_local2085274023_0001
2020-03-05 23:03:48,735 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-05 23:03:48,740 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,740 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,741 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-05 23:03:48,764 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-05 23:03:48,764 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:48,779 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,779 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,792 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,796 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/hadoop-policy.xml:0+11392
2020-03-05 23:03:48,843 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,843 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,843 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,843 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,843 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:48,846 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:48,857 INFO mapred.LocalJobRunner:
2020-03-05 23:03:48,858 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:48,858 INFO mapred.MapTask: Spilling map output
2020-03-05 23:03:48,858 INFO mapred.MapTask: bufstart = 0; bufend = 17; bufvoid = 104857600
2020-03-05 23:03:48,858 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-05 23:03:48,868 INFO mapred.MapTask: Finished spill 0
2020-03-05 23:03:48,875 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000000_0 is done. And is in the process of committing
2020-03-05 23:03:48,877 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:48,877 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000000_0' done.
2020-03-05 23:03:48,882 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000000_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=329310
		FILE: Number of bytes written=841095
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=265
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=134
		Combine input records=1
		Combine output records=1
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=260046848
	File Input Format Counters
		Bytes Read=11392
2020-03-05 23:03:48,882 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:48,883 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:48,883 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,883 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,884 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,885 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/capacity-scheduler.xml:0+8260
2020-03-05 23:03:48,932 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,932 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,932 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,932 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,932 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:48,932 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:48,936 INFO mapred.LocalJobRunner:
2020-03-05 23:03:48,937 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:48,939 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000001_0 is done. And is in the process of committing
2020-03-05 23:03:48,940 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:48,940 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000001_0' done.
2020-03-05 23:03:48,941 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000001_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=338782
		FILE: Number of bytes written=841133
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=220
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=139
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=365428736
	File Input Format Counters
		Bytes Read=8260
2020-03-05 23:03:48,941 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:48,941 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:48,942 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:48,942 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:48,942 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:48,943 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/kms-acls.xml:0+3518
2020-03-05 23:03:48,999 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:48,999 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:48,999 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:48,999 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:48,999 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,000 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,002 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,002 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,004 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000002_0 is done. And is in the process of committing
2020-03-05 23:03:49,005 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,005 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000002_0' done.
2020-03-05 23:03:49,006 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000002_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=343512
		FILE: Number of bytes written=841171
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=135
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=129
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=470810624
	File Input Format Counters
		Bytes Read=3518
2020-03-05 23:03:49,006 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:49,006 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,007 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,007 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,007 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,008 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/hdfs-site.xml:0+775
2020-03-05 23:03:49,061 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,061 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,061 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,061 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,061 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,061 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,062 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,062 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,065 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000003_0 is done. And is in the process of committing
2020-03-05 23:03:49,066 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,066 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000003_0' done.
2020-03-05 23:03:49,067 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000003_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=345499
		FILE: Number of bytes written=841209
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=21
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=576192512
	File Input Format Counters
		Bytes Read=775
2020-03-05 23:03:49,067 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,067 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,068 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,068 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,068 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,070 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/core-site.xml:0+774
2020-03-05 23:03:49,114 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,114 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,114 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,114 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,114 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,115 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,116 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,116 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,196 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000004_0 is done. And is in the process of committing
2020-03-05 23:03:49,197 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,197 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000004_0' done.
2020-03-05 23:03:49,197 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000004_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=346973
		FILE: Number of bytes written=841247
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=20
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=681574400
	File Input Format Counters
		Bytes Read=774
2020-03-05 23:03:49,197 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,198 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,199 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,199 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,199 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,201 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/mapred-site.xml:0+758
2020-03-05 23:03:49,255 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,255 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,255 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,255 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,255 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,256 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,258 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,259 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,261 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000005_0 is done. And is in the process of committing
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,262 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000005_0' done.
2020-03-05 23:03:49,262 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000005_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=348431
		FILE: Number of bytes written=841285
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=21
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=132
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=786956288
	File Input Format Counters
		Bytes Read=758
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,262 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,263 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,263 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,264 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,265 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/yarn-site.xml:0+690
2020-03-05 23:03:49,316 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,316 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,316 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,316 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,316 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,316 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,318 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,318 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,320 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000006_0 is done. And is in the process of committing
2020-03-05 23:03:49,321 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,321 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000006_0' done.
2020-03-05 23:03:49,322 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000006_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=349821
		FILE: Number of bytes written=841323
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=19
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=130
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=892338176
	File Input Format Counters
		Bytes Read=690
2020-03-05 23:03:49,322 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,322 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,323 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,323 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,323 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,324 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/kms-site.xml:0+682
2020-03-05 23:03:49,368 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,368 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,368 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,368 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,368 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,369 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,370 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,370 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,372 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000007_0 is done. And is in the process of committing
2020-03-05 23:03:49,374 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,375 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000007_0' done.
2020-03-05 23:03:49,376 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000007_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=351203
		FILE: Number of bytes written=841361
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=20
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=129
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=997720064
	File Input Format Counters
		Bytes Read=682
2020-03-05 23:03:49,376 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,376 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,377 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,377 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,378 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,379 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/input/httpfs-site.xml:0+620
2020-03-05 23:03:49,441 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,441 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,441 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,441 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,441 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,442 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,443 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,443 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,445 INFO mapred.Task: Task:attempt_local2085274023_0001_m_000008_0 is done. And is in the process of committing
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,446 INFO mapred.Task: Task 'attempt_local2085274023_0001_m_000008_0' done.
2020-03-05 23:03:49,446 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_m_000008_0: Counters: 18
	File System Counters
		FILE: Number of bytes read=352011
		FILE: Number of bytes written=841399
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=17
		Map output records=0
		Map output bytes=0
		Map output materialized bytes=6
		Input split bytes=132
		Combine input records=0
		Combine output records=0
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1103101952
	File Input Format Counters
		Bytes Read=620
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,446 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-05 23:03:49,448 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-05 23:03:49,448 INFO mapred.LocalJobRunner: Starting task: attempt_local2085274023_0001_r_000000_0
2020-03-05 23:03:49,454 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,454 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,454 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,457 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@62e1023a
2020-03-05 23:03:49,459 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,473 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-05 23:03:49,476 INFO reduce.EventFetcher: attempt_local2085274023_0001_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-05 23:03:49,497 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000003_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,500 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000003_0
2020-03-05 23:03:49,502 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->2
2020-03-05 23:03:49,504 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000006_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,504 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000006_0
2020-03-05 23:03:49,504 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 2, commitMemory -> 2, usedMemory ->4
2020-03-05 23:03:49,505 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000002_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,506 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000002_0
2020-03-05 23:03:49,506 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 3, commitMemory -> 4, usedMemory ->6
2020-03-05 23:03:49,507 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000005_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,507 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000005_0
2020-03-05 23:03:49,507 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 4, commitMemory -> 6, usedMemory ->8
2020-03-05 23:03:49,508 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000008_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,508 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000008_0
2020-03-05 23:03:49,509 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 5, commitMemory -> 8, usedMemory ->10
2020-03-05 23:03:49,510 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000001_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,510 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000001_0
2020-03-05 23:03:49,510 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 6, commitMemory -> 10, usedMemory ->12
2020-03-05 23:03:49,511 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000004_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,511 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000004_0
2020-03-05 23:03:49,511 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 7, commitMemory -> 12, usedMemory ->14
2020-03-05 23:03:49,512 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000007_0 decomp: 2 len: 6 to MEMORY
2020-03-05 23:03:49,512 INFO reduce.InMemoryMapOutput: Read 2 bytes from map-output for attempt_local2085274023_0001_m_000007_0
2020-03-05 23:03:49,512 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 2, inMemoryMapOutputs.size() -> 8, commitMemory -> 14, usedMemory ->16
2020-03-05 23:03:49,513 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local2085274023_0001_m_000000_0 decomp: 21 len: 25 to MEMORY
2020-03-05 23:03:49,513 INFO reduce.InMemoryMapOutput: Read 21 bytes from map-output for attempt_local2085274023_0001_m_000000_0
2020-03-05 23:03:49,513 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 21, inMemoryMapOutputs.size() -> 9, commitMemory -> 16, usedMemory ->37
2020-03-05 23:03:49,514 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-05 23:03:49,514 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,514 INFO reduce.MergeManagerImpl: finalMerge called with 9 in-memory map-outputs and 0 on-disk map-outputs
2020-03-05 23:03:49,519 INFO mapred.Merger: Merging 9 sorted segments
2020-03-05 23:03:49,519 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 10 bytes
2020-03-05 23:03:49,519 INFO reduce.MergeManagerImpl: Merged 9 segments, 37 bytes to disk to satisfy reduce memory limit
2020-03-05 23:03:49,519 INFO reduce.MergeManagerImpl: Merging 1 files, 25 bytes from disk
2020-03-05 23:03:49,520 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-05 23:03:49,520 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,520 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 10 bytes
2020-03-05 23:03:49,520 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,530 INFO Configuration.deprecation: mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
2020-03-05 23:03:49,531 INFO mapred.Task: Task:attempt_local2085274023_0001_r_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,531 INFO mapred.LocalJobRunner: 9 / 9 copied.
2020-03-05 23:03:49,531 INFO mapred.Task: Task attempt_local2085274023_0001_r_000000_0 is allowed to commit now
2020-03-05 23:03:49,533 INFO output.FileOutputCommitter: Saved output of task 'attempt_local2085274023_0001_r_000000_0' to file:/home/zaky/software/bigdata/hadoop-3.2.1/grep-temp-2147140595
2020-03-05 23:03:49,533 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-05 23:03:49,533 INFO mapred.Task: Task 'attempt_local2085274023_0001_r_000000_0' done.
2020-03-05 23:03:49,534 INFO mapred.Task: Final Counters for attempt_local2085274023_0001_r_000000_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=352397
		FILE: Number of bytes written=841547
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=73
		Reduce input records=1
		Reduce output records=1
		Spilled Records=1
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=1103101952
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=123
2020-03-05 23:03:49,534 INFO mapred.LocalJobRunner: Finishing task: attempt_local2085274023_0001_r_000000_0
2020-03-05 23:03:49,534 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-05 23:03:49,739 INFO mapreduce.Job: Job job_local2085274023_0001 running in uber mode : false
2020-03-05 23:03:49,742 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-05 23:03:49,744 INFO mapreduce.Job: Job job_local2085274023_0001 completed successfully
2020-03-05 23:03:49,768 INFO mapreduce.Job: Counters: 30
	File System Counters
		FILE: Number of bytes read=3457939
		FILE: Number of bytes written=8412770
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=738
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=73
		Input split bytes=1185
		Combine input records=1
		Combine output records=1
		Reduce input groups=1
		Reduce shuffle bytes=73
		Reduce input records=1
		Reduce output records=1
		Spilled Records=2
		Shuffled Maps =9
		Failed Shuffles=0
		Merged Map outputs=9
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=7237271552
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=27469
	File Output Format Counters
		Bytes Written=123
2020-03-05 23:03:49,869 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,876 INFO input.FileInputFormat: Total input files to process : 1
2020-03-05 23:03:49,877 INFO mapreduce.JobSubmitter: number of splits:1
2020-03-05 23:03:49,888 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local703196677_0002
2020-03-05 23:03:49,889 INFO mapreduce.JobSubmitter: Executing with tokens: []
2020-03-05 23:03:49,936 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
2020-03-05 23:03:49,936 INFO mapreduce.Job: Running job: job_local703196677_0002
2020-03-05 23:03:49,936 INFO mapred.LocalJobRunner: OutputCommitter set in config null
2020-03-05 23:03:49,937 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,937 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,937 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
2020-03-05 23:03:49,938 INFO mapred.LocalJobRunner: Waiting for map tasks
2020-03-05 23:03:49,938 INFO mapred.LocalJobRunner: Starting task: attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,939 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,939 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,939 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,940 INFO mapred.MapTask: Processing split: file:/home/zaky/software/bigdata/hadoop-3.2.1/grep-temp-2147140595/part-r-00000:0+111
2020-03-05 23:03:49,959 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
2020-03-05 23:03:49,959 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
2020-03-05 23:03:49,959 INFO mapred.MapTask: soft limit at 83886080
2020-03-05 23:03:49,959 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
2020-03-05 23:03:49,959 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
2020-03-05 23:03:49,960 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-03-05 23:03:49,965 INFO mapred.LocalJobRunner:
2020-03-05 23:03:49,965 INFO mapred.MapTask: Starting flush of map output
2020-03-05 23:03:49,965 INFO mapred.MapTask: Spilling map output
2020-03-05 23:03:49,965 INFO mapred.MapTask: bufstart = 0; bufend = 17; bufvoid = 104857600
2020-03-05 23:03:49,965 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-03-05 23:03:49,965 INFO mapred.MapTask: Finished spill 0
2020-03-05 23:03:49,966 INFO mapred.Task: Task:attempt_local703196677_0002_m_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,967 INFO mapred.LocalJobRunner: map
2020-03-05 23:03:49,967 INFO mapred.Task: Task 'attempt_local703196677_0002_m_000000_0' done.
2020-03-05 23:03:49,968 INFO mapred.Task: Final Counters for attempt_local703196677_0002_m_000000_0: Counters: 17
	File System Counters
		FILE: Number of bytes read=669252
		FILE: Number of bytes written=1677613
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=1
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=144
		Combine input records=0
		Spilled Records=1
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=315621376
	File Input Format Counters
		Bytes Read=123
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: Finishing task: attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: map task executor complete.
2020-03-05 23:03:49,968 INFO mapred.LocalJobRunner: Waiting for reduce tasks
2020-03-05 23:03:49,969 INFO mapred.LocalJobRunner: Starting task: attempt_local703196677_0002_r_000000_0
2020-03-05 23:03:49,969 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 2
2020-03-05 23:03:49,969 INFO output.FileOutputCommitter: FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-03-05 23:03:49,970 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
2020-03-05 23:03:49,970 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@3caac82b
2020-03-05 23:03:49,970 WARN impl.MetricsSystemImpl: JobTracker metrics system already initialized!
2020-03-05 23:03:49,971 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=1271293568, maxSingleShuffleLimit=317823392, mergeThreshold=839053760, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-03-05 23:03:49,971 INFO reduce.EventFetcher: attempt_local703196677_0002_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-03-05 23:03:49,972 INFO reduce.LocalFetcher: localfetcher#2 about to shuffle output of map attempt_local703196677_0002_m_000000_0 decomp: 21 len: 25 to MEMORY
2020-03-05 23:03:49,973 INFO reduce.InMemoryMapOutput: Read 21 bytes from map-output for attempt_local703196677_0002_m_000000_0
2020-03-05 23:03:49,973 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 21, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->21
2020-03-05 23:03:49,973 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
2020-03-05 23:03:49,973 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,973 INFO reduce.MergeManagerImpl: finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-03-05 23:03:49,974 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,974 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 11 bytes
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merged 1 segments, 21 bytes to disk to satisfy reduce memory limit
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merging 1 files, 25 bytes from disk
2020-03-05 23:03:49,974 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
2020-03-05 23:03:49,974 INFO mapred.Merger: Merging 1 sorted segments
2020-03-05 23:03:49,975 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 11 bytes
2020-03-05 23:03:49,975 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,977 INFO mapred.Task: Task:attempt_local703196677_0002_r_000000_0 is done. And is in the process of committing
2020-03-05 23:03:49,978 INFO mapred.LocalJobRunner: 1 / 1 copied.
2020-03-05 23:03:49,978 INFO mapred.Task: Task attempt_local703196677_0002_r_000000_0 is allowed to commit now
2020-03-05 23:03:49,979 INFO output.FileOutputCommitter: Saved output of task 'attempt_local703196677_0002_r_000000_0' to file:/home/zaky/software/bigdata/hadoop-3.2.1/output
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: reduce > reduce
2020-03-05 23:03:49,979 INFO mapred.Task: Task 'attempt_local703196677_0002_r_000000_0' done.
2020-03-05 23:03:49,979 INFO mapred.Task: Final Counters for attempt_local703196677_0002_r_000000_0: Counters: 24
	File System Counters
		FILE: Number of bytes read=669334
		FILE: Number of bytes written=1677661
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=25
		Reduce input records=1
		Reduce output records=1
		Spilled Records=1
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=315621376
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Output Format Counters
		Bytes Written=23
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: Finishing task: attempt_local703196677_0002_r_000000_0
2020-03-05 23:03:49,979 INFO mapred.LocalJobRunner: reduce task executor complete.
2020-03-05 23:03:50,937 INFO mapreduce.Job: Job job_local703196677_0002 running in uber mode : false
2020-03-05 23:03:50,937 INFO mapreduce.Job:  map 100% reduce 100%
2020-03-05 23:03:50,938 INFO mapreduce.Job: Job job_local703196677_0002 completed successfully
2020-03-05 23:03:50,941 INFO mapreduce.Job: Counters: 30
	File System Counters
		FILE: Number of bytes read=1338586
		FILE: Number of bytes written=3355274
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
	Map-Reduce Framework
		Map input records=1
		Map output records=1
		Map output bytes=17
		Map output materialized bytes=25
		Input split bytes=144
		Combine input records=0
		Combine output records=0
		Reduce input groups=1
		Reduce shuffle bytes=25
		Reduce input records=1
		Reduce output records=1
		Spilled Records=2
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=0
		Total committed heap usage (bytes)=631242752
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=123
	File Output Format Counters
		Bytes Written=23
$ 
```

Hasil akan diletakkan pada direktori `output`:

```bash
$ ls -la output/
total 20
drwxr-xr-x  2 zaky zaky 4096 Mar  5 23:03 .
drwxr-xr-x 11 zaky zaky 4096 Mar  5 23:03 ..
-rw-r--r--  1 zaky zaky   11 Mar  5 23:03 part-r-00000
-rw-r--r--  1 zaky zaky   12 Mar  5 23:03 .part-r-00000.crc
-rw-r--r--  1 zaky zaky    0 Mar  5 23:03 _SUCCESS
-rw-r--r--  1 zaky zaky    8 Mar  5 23:03 ._SUCCESS.crc
$  cat output/part-r-00000
1	dfsadmin
$
```


