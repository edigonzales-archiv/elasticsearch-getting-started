# elasticsearch-getting-started

curl -X DELETE "localhost:9200/address?pretty"

curl -X PUT "localhost:9200/address?pretty"

curl -H "Content-Type: application/json" -X POST "localhost:9200/address/_doc/_bulk?pretty&refresh" --data-binary "@addresses.json"

curl -X GET "localhost:9200/address/_search?pretty" -H 'Content-Type: application/json' -d'
{
  "query": { "match_all": {} }
}
'

curl -X GET "localhost:9200/address/_search?pretty" -H 'Content-Type: application/json' -d'
{
  "query": {
    "simple_query_string" : {
      "query": "+ga* +ober*",
      "fields": [ "full_address" ] 
    }
  },
  "highlight" :  {
      "fields" : {
          "*" : {}
      }
  }
}
'

elasticsearch.yml:
http.cors.enabled : true
http.cors.allow-origin : "*"
