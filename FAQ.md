# Frequently Asked Questions #



## What is the purpose of the IATI Exchange tool ? ##
IATI Exchange aims to  simplify the translation of aid data from a standard database like MySQL, MS SQL, Oracle, Postgres to IATI XML.

## Who should use this tool ? ##
Any organization that wants to export their aid data into an IATI XMLfeed. Most of the organizations use a relational database for storing information about their aid projects and this is basically the only pre-requirement for using the IATI Exchange tool.

## Who can configure this tool ? ##
A database / system administrator. Some SQL knowledge is needed as well as knowledge on the database structure that the organization is using for storing the aid project information. With this knowledge one can create an "export configuration".

## What is an "export configuration" ? ##
An "export configuration" represents the information that is needed to run a transformation (from database to IATI XML). For the same database there could be several "export configurations" created (exporting different information or different flavors of XML )
A configuration contains the following information:
  * database connection information
  * the list of the IATI fields that will be used
  * small SQL queries that gather information for a specific IATI field
  * the mapping between the SQL query results and the IATI attributes and fields

## How is an export executed ? ##
Either **manually** from the user interface OR via URLs (REST-style) by another software tool that wants to get the results of the transformation **automatically**

## What can a user do from the UI ? ##
A user can create an "export configuration" by using the UI. He can also run the transformation from the UI.

## Can the exported projects be filtered by a certain criteria before exporting ? ##
Yes.
One can create a configuration in which the filters are specified at runtime ( when the export is executed ). This means that for example one can first export all projects with status "Implementation" and after that export all projects with status "Completion" without needing to change the configuration in-between. This can also prove to be useful, for example, if, in a certain translation of your data to IATI, you only want to export transactions that are in a certain period of time or after a certain date.

## What kind of database can be used as a source ? ##
Any relational database for which a JDBC database driver exists.

## What technologies does IATI Exchange employ ? ##
IATI Exchange is based on Java technologies like Wicket, Jetty, Maven or JDBC. So all you need in order to run this tool is a Java Virtual Machine and Maven. The tools was developed and tested under JAVA 1.6 .
