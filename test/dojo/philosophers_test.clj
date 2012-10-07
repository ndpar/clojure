(ns dojo.philosophers-test
  (:use clojure.test
        dojo.philosophers))

(deftest eat-test
         (let [forks (forks 5)]
           (eat 0 forks)
           (is (= [1 1 0 0 0] (mapv deref forks)))
           (eat 1 forks)
           (is (= [1 2 1 0 0] (mapv deref forks)))))

(comment deftest philosopher-test
         (let [forks (forks 5)]
           (philosopher 0 7 forks)
           (is (= [7 7 0 0 0] (mapv deref forks)))))

(deftest dine-test
         (let [forks (forks 5)]
           (dine 5 3 forks)
           (is (= [6 6 6 6 6] (mapv deref forks)))))

