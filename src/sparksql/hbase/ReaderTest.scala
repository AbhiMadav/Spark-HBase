import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue.Type
import org.apache.hadoop.hbase.HConstants
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.CellUtil

import org.apache.spark._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types._
import org.apache.hadoop.hbase.mapreduce.TableInputFormat

import scala.collection.JavaConverters._

import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext

object HBaseInput {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local");
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    var hbasetable = sqlContext.read.format("sparksql.hbase").options(Map(
      "sparksql_table_schema" -> "(key string, col1data1 string, col2data2 string)",
      "hbase_table_name" -> "testTable",
      "hbase_table_schema" -> "(:key , colFam1:data1 , colFam2:data2)",
      "zookeeper_quorum" -> "localhost")).load()

    hbasetable.show()
  }

}