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
                .insertOne({
                    "requester": req.params.requester,
                    "requestee": req.params.requestee,
                    "requestedDay": req.params.requestedday,
                    "isAccepted": "false"
                }, (error, result) => {
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
            database.collection("RequestCollection").findOne({ $and: [{ "requestee": req.params.requestee }, { "requester": req.params.requester }] }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                if (result == null) {
                    res.send("null");
                }
                else {
                    console.log(result);
                    res.send(result.isAccepted);
                }

            });

        }),
        app.get("/requestlist/:requestee", (req, res) => {
            database.collection("RequestCollection").findOne({ $and: [{ "requestee": req.params.requestee }, { "isAccepted": "false" }] }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                if (result) {
                    console.log(result);
                    res.send(result["requester"]);
                }
                else {
                    res.send(null)
                }
            });
        })
        ,
        app.get("/requesteddaylist/:requestee", (req, res) => {
            database.collection("RequestedDayCollection").findOne({ $and: [{ "requestee": req.params.requestee }, { "isAccepted": "false" }] }, (error, result) => {
                if (error) {
                    return res.status(500).send(error);
                }
                if (result) {
                    listOfResults = [];
                    console.log(result);
                    listOfResults.push(result["requestedDay"]);
                    listOfResults.push(result["requester"]);
                    res.json(listOfResults);
                }
                else {
                    res.jsonp([null])
                }
            });
        }),
        app.get("/profile/:requestee", (req, res) => {
            database.collection("RequestedDayCollection")
                .find({ $and: [{ "requestee": req.params.requestee }, { "isAccepted": "true" }] })
                .toArray((error, result) => {
                    if (error) {
                        return res.status(500).send(error);
                    }
                    if (result) {
                        res.jsonp({result});
                    }
                    else {
                        res.jsonp(null)
                    }
                })
        }),
        app.get("/acceptdayrequest/:requester&:requestee&:requestedday", (req, res) => {
            database.collection("RequestedDayCollection").updateMany({ "requester": req.params.requester, "requestee": req.params.requestee, "requestedDay": req.params.requestedday },
                { $set: { "isAccepted": "true" } }, { upsert: false }, (error, result) => {

                    if (error) {
                        return res.status(500).send(error);
                    }
                    switch (req.params.requestedday) {
                        case "monday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.0": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "tuesday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.1": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "wednesday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.2": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "thursday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.3": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "friday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.4": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "saturday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.5": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });
                            break;
                        case "sunday":
                            database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.6": false } }, (error2, result2) => {
                                if (error2) {
                                    return res.status(500).send(error2);
                                }
                                console.log("result: " + result + "\n" + "result2: " + result2)
                                res.send(result + result2);
                            });

                    }

                })
        })
}),
    app.get("/canceldayrequest/:requestee&:requester&:requestedday", (req, res) => {
        database.collection("RequestedDayCollection").deleteMany({ "requester": req.params.requester, "requestee": req.params.requestee, "requestedDay": req.params.requestedday }, (error, result) => {

                if (error) {
                    return res.status(500).send(error);
                }
                switch (req.params.requestedday) {
                    case "monday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.0": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "tuesday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.1": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "wednesday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.2": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "thursday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.3": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "friday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.4": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "saturday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.5": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                        break;
                    case "sunday":
                        database.collection("UserCollection").updateOne({ "name": req.params.requestee, "availableDays": false }, { $set: { "availableDays.6": true } }, (error2, result2) => {
                            if (error2) {
                                return res.status(500).send(error2);
                            }
                            console.log("result: " + result + "\n" + "result2: " + result2)
                            res.jsonp(result);
                        });
                }
            })
    });
