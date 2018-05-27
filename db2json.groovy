@GrabConfig(systemClassLoader=true)
@Grab(group='org.postgresql', module='postgresql', version='42.2.2')

import groovy.sql.*
import groovy.json.*

def dbUrl = "jdbc:postgresql://192.168.50.6/pub"
def dbUser = "ddluser"
def dbPassword = "ddluser"
def dbDriver = "org.postgresql.Driver"

def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)


def stmt = """
SELECT 
    row_to_json(t) AS foo
FROM 
(
    SELECT 
        adr.t_id::text,
        egid::text,
        strassenname,
        hausnummer,
        plz::text,
        ortschaft,
        gemeindename,
        strassenname || ' ' || hausnummer || ', ' || plz::text || ' ' || ortschaft AS full_address
    FROM 
        agi_mopublic_pub.mopublic_gebaeudeadresse AS adr
        LEFT JOIN agi_mopublic_pub.mopublic_gemeindegrenze AS gem
        ON ST_Intersects(adr.lage, gem.geometrie)
) as t
"""
sql.eachRow(stmt) { row ->
    println '{"index": {}}'
    println row['foo']
}
sql.close()
