# Room Occupancy Optimization Example [![GitHub license](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://github.com/aweris/roo/blob/master/LICENSE)

The idea of this project to create an example application solving "Room Occupancy Optimization."

## Problem

Build a room occupancy optimization tool for one of our hotel clients! Our customer has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.
Our hotel clients have two different categories of rooms: Premium and Economy. Our hotels want their customers to be satisfied: they will not book a customer willing to pay over EUR 100 for the night into an Economy room. But they will book lower paying customers into Premium rooms if these rooms would be empty and all Economy rooms will be filled by low paying customers. Highest paying customers below EUR 100 will get preference for the “upgrade.” Customers always only have one specific price they are willing to pay for the night.

## Prerequisites

For building or developing this application, you need to install and configure following `Java 11`, `build-essential`(for __Makefile__) and `docker`.

## Installing / Getting started

A quick introduction of the minimal setup you need to get  Roo up & running.

```bash
> make docker
```

When you run this command, you'll `build` and `dockerize` application. After starting the application, you can access the application server  from `localhost:8080` and mongo-express `localhost:8081` 

For other commands like `start` `stop` development db, please check out `Makefile`

## Docs

You can find more info in [docs](https://documenter.getpostman.com/view/5717174/Rzfdqr7y) about rest API