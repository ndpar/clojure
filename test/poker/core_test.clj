(ns poker.core-test
  (:use clojure.test
        poker.core))

(defn hand
  "Convert string to hand format"
  [hand-string]
  (clojure.string/split hand-string #" "))

(let [sf (hand "6C 7C 8C 9C TC")
      fk (hand "9D 9H 9S 9C 7D")
      fh (hand "TD TC TH 7C 7D")]

  (deftest flush-test
    (testing "Straight flush is flush"
      (is (flush? sf)))
    (testing "Full house is not flush"
      (is (not (flush? fh)))))

  (deftest straight-test
    (testing "Consecutive numbers is straight"
      (is (straight? [10 9 8 7 6])))
    (testing "Two eights is not sraight"
      (is (not (straight? [9 8 8 7 6])))))

  (deftest card-ranks-test
    (testing "Straight flush card ranks"
      (is (= [10 9 8 7 6] (card-ranks sf))))
    (testing "Four of a kind card ranks"
      (is (= [9 9 9 9 7] (card-ranks fk))))
    (testing "Full house card ranks"
      (is (= [10 10 10 7 7] (card-ranks fh)))))

  (deftest hand-rank-test
    (testing "Straight flush rank"
      (is (= [8 10] (hand-rank sf))))
    (testing "Four of a kind rank"
      (is (= [7 9 7] (hand-rank fk))))
    (testing "Full house rank"
      (is (= [6 10 7] (hand-rank fh)))))

  (deftest poker-test
    (testing "Straight flush wins over Four of a kind and Full house"
      (is (= sf (poker [sf fk fh]))))
    (testing "Four of a kind wins Full house"
      (is (= fk (poker [fk fh]))))
    (testing "Full house wins itself"
      (is (= fh (poker [fh fh]))))
    (testing "Extreme case: Single hand is allowed in poker function"
      (is (= sf (poker [sf]))))
    (testing "Extreme case: 100 hands are allowed in poker function"
      (is (= sf (poker (conj (vec (repeat 99 fh)) sf)))))))
