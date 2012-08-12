(ns poker.core-test
  (:use clojure.test
        poker.core))

(let [sf (clojure.string/split "6C 7C 8C 9C TC" #" ")
      fk (clojure.string/split "9D 9H 9S 9C 7D" #" ")
      fh (clojure.string/split "6C 7C 8C 9C TC" #" ")]

  (deftest poker-test
    (testing "Straight flush wins over Four of a kind and Full house"
      (is (= sf (poker [sf fk fh]))))
    (testing "Four of a kind wins Full house"
      (is (= fk (poker [fk fh]))))
    (testing "Full house wins itself"
      (is (= fh (poker [fh fh]))))))
