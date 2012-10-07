(ns joy.qsort-test
  (:use joy.qsort clojure.test))

(deftest qsort-test
         (is (= [1 2 3 4] (qsort [2 1 4 3]))))

(deftest order-statistics-test
         (is (= [1 2] (take 2 (qsort [2 1 4 3])))))

