const Express = require("express");
const BodyParser = require("body-parser");
const MongoClient = require("mongodb").MongoClient;
const ObjectId = require("mongodb").ObjectID;

const CONNECTION_URL = "mongodb+srv://dbretriever:dbretriever@sme-oda7i.mongodb.net/test?retryWrites=true";
const DATABASE_NAME = "SMEDB";

var app = Express();

app.use(BodyParser.json());
app.use(BodyParser.urlencoded({ extended: true }));

var database, collection;

app.listen(8000, () => {
    MongoClient.connect(CONNECTION_URL, { useNewUrlParser: true }, (error, client) => {
        if (error) {
            throw error;
        }
        database = client.db(DATABASE_NAME);
        collection = database.collection("UserCollection");
        console.log("Connected to `" + DATABASE_NAME + "`!");
    }),
        app.get("/user/id/:id", (req, res) => {
            collection.findOne({ "_id": new ObjectId(req.params.id) }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user/job/:job", (req, res) => {
            collection.find({ "job": req.params.job }).toArray((error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user", (req, res) => {
            collection.find({}).toArray((error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user/jobs", (req, res) => {
            collection.distinct("job", (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user/prices/desc", (req, res) => {
            collection.find().sort({ price: -1 }).toArray((error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user/prices/asc", (req, res) => {
            collection.find().sort({ price: 1 }).toArray((error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/user/filter/:city&:price", (req, res) => {
            if (req.params.price === "asc") {
                collection.find({ "city": req.params.city }).sort({ price: 1 }).toArray((error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send({ result });
                });
            }
            else if (req.params.price === "desc") {
                collection.find({ "city": req.params.city }).sort({ price: -1 }).toArray((error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send({ result });
                });
            }
        }),
        app.get("/user/filter_3/:city&:price&:job", (req, res) => {
            if (req.params.price === "asc") {
                collection.find({ "job": req.params.job, "city": req.params.city }).sort({ price: 1 }).toArray((error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send({ result });
                });
            }
            else if (req.params.price === "desc") {
                collection.find({ "job": req.params.job, "city": req.params.city }).sort({ price: -1 }).toArray((error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send({ result });
                });
            }
        }),
        app.get("/user/name/:username", (req, res) => {
            collection.findOne({ "name": req.params.username }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send({ result });
            });
        }),
        app.get("/request/:requester&:requestee", (req, res) => {
            database.collection("RequestCollection").insertOne({ "requester": req.params.requester, "requestee": req.params.requestee, "isAccepted": "false" }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send(result)
            });
        }),
        app.get("/requestday/:requester&:requestee&:requestedday", (req, res) => {
            database.collection("RequestedDayCollection")
            .insertOne({ "requester": req.params.requester,
             "requestee": req.params.requestee,
              "requestedDay":req.params.requestedday,
               "isAccepted": "false" }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                res.send(result)
            });
        }),
        app.get("/accept/:requester&:requestee", (req, res) => {
            console.log(req.params.requester, req.params.requestee)
            database.collection("RequestCollection").updateMany({ "requester": req.params.requester, "requestee": req.params.requestee },
                { $set: { "isAccepted": "true" } }, { upsert: false }, (error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send(result)
                });

        }),
        app.get("/isaccepted/:requester&:requestee", (req, res) => {
            console.log(req.params.requester, req.params.requestee)
            database.collection("RequestCollection").findOne({ $and: [{"requestee": req.params.requestee} ,{"requester": req.params.requester}] }, (error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    if(result==null){
                        res.send("null");
                    }
                    else{
                        console.log(result);
                        res.send(result.isAccepted);
                    }
                        
                });

        }),
        app.get("/requestlist/:requestee", (req, res) => {
            database.collection("RequestCollection").findOne({ $and: [{"requestee": req.params.requestee}, {"isAccepted": "false"}]}, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                if(result){
                    console.log(result);
                    res.send(result["requester"]);
                }
                else{
                    res.send(null)
                }
            });
        })
        ,
        app.get("/requesteddaylist/:requestee", (req, res) => {
            database.collection("RequestedDayCollection").findOne({ $and: [{"requestee": req.params.requestee}, {"isAccepted": "false"}]}, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                if(result){
                    listOfResults=[];
                    console.log(result);
                    listOfResults.push(result["requestedDay"]);
                    listOfResults.push(result["requester"]);
                    res.json(listOfResults);
                }
                else{
                    res.jsonp([null])
                }
            });
        }),
        app.get("/acceptdayrequest/:requester&:requestee&:requestedday", (req, res) => {
            database.collection("RequestedDayCollection").updateMany({ "requester": req.params.requester, "requestee": req.params.requestee, "requestedDay":req.params.requestedday },
                { $set: { "isAccepted": "true" } }, { upsert: false }, (error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    res.send(result)
                });
        })
});
