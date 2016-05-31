(ns dojo.combinators-test
  (:use clojure.test dojo.combinators))

(deftest factorial-test
  (is (= 120 (factorial 5))))

(deftest triangular-test
  ;(is (= ? (triangular 30000))) ; StackOverflowError
  (is (= 4501501 (triangular 3000))))
