# octowight [![Build Status](https://travis-ci.org/jasperavisser/octowight-example.svg?branch=master)](https://travis-ci.org/jasperavisser/octowight-example) 

## Requirements

## Installation

### Bootstrap
Follow the bootstrap instructions for *octowight*.

### Build 
```bash
gradle assemble
```

### Start infrastructure containers
```bash
bash bin/build-images.sh
docker-compose -f infrastructure.yml up -d
```

### Run integration tests
```bash
gradle IT
```

### Run integration tests in IDE
Run \*IT.scala with environment variable *INFRASTRUCTURE_HOST = your docker host* (usually localhost).

## TODO
* Deduplicate some of the gradle code
* Rename sample -> example
* Write a proper README for both projects
