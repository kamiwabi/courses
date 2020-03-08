# Operasi Dasar MongoDB

```bash
> use wabi
switched to db wabi
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
> db.training.insert({name: "Apache Hadoop + MongoDB", duration: 10})
WriteResult({ "nInserted" : 1 })
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
wabi    0.000GB
> db.training.insert({
... name: "Corda Blockchain", duration: 15
... show dbs^C

> db.training.insert({
... name: "Corda Blockchain",
... duration: 15,
... trainer: "bpdp"
... })
WriteResult({ "nInserted" : 1 })
> db.training.find()
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
>
```
