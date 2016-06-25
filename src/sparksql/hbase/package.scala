package sparksql.hbase

import org.apache.spark.sql.SQLContext
import scala.collection.immutable.HashMap

package object hbase {

  case class Parameter(name: String)

  protected val SPARK_SQL_TABLE_SCHEMA = Parameter("sparksql_table_schema")
  protected val HBASE_TABLE_NAME = Parameter("hbase_table_name")
  protected val HBASE_TABLE_SCHEMA = Parameter("hbase_table_schema")
  protected val ROW_RANGE = Parameter("row_range")
  protected val ZOOKEEPER_QUORUM = Parameter("zookeeper_quorum")
  
  /**
   * Adds a method, `hbaseTable`, to SQLContext that allows reading data stored in hbase table.
   */
  implicit class HBaseContext(sqlContext: SQLContext) {
    def hbaseTable(sparksqlTableSchema: String, hbaseTableName: String, hbaseTableSchema: String, zookeeperQuorum: String, rowRange: String = "->") = {
      var params = new HashMap[String, String]
      params += (SPARK_SQL_TABLE_SCHEMA.name -> sparksqlTableSchema)
      params += (HBASE_TABLE_NAME.name -> hbaseTableName)
      params += (HBASE_TABLE_SCHEMA.name -> hbaseTableSchema)
      params += (ZOOKEEPER_QUORUM.name -> zookeeperQuorum)
      //get star row and end row
      params += (ROW_RANGE.name -> rowRange)
      sqlContext.baseRelationToDataFrame(HBaseRelation(params)(sqlContext));
    }
  }
}