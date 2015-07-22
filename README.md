# decisiontree

This project builds a simple binary regression tree from an input data set.

It is still a work in progress - currently it artificially limits the tree size to avoid overfitting on the training set.
Furthermore it only supports regression (and not classification) problems.

The implementation is based on the algorithm in Section 8.1 of this book [1];

## Setup
This project requires that you have maven installed and present in your $PATH. See - https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

```bash
git clone https://github.com/kostadinstoilov/decisiontree.git
cd decisiontree
mvn compile
```

### Usage

```bash
mvn exec:java -Dexec.mainClass="com.ontotext.kstoilov.interview.task2.Task2"

### References

1. http://www-bcf.usc.edu/~gareth/ISL/
