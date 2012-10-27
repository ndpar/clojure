(ns dojo.parens-test
  (:use clojure.test dojo.parens))

(deftest group-triplet-test
  (is (= '(10 + (20 x 30) + 40 x 50) (group-triplet '(10 + 20 x 30 + 40 x 50) 3))))

(deftest group-multiplications-test
  (is (= '(10 + (20 x 30) + (40 x 50)) (group-multiplications '(10 + 20 x 30 + 40 x 50))))
  (is (= '(10 + (20 x (30 x (40 x 50)))) (group-multiplications '(10 + 20 x 30 x 40 x 50)))))

(deftest group-additions-test
  (is (= '((10 + 20) x (30 + 40) x 50) (group-additions '(10 + 20 x 30 + 40 x 50))))
  (is (= '((((10 - 20) + 30) + 40) x 50) (group-additions '(10 - 20 + 30 + 40 x 50)))))

(deftest group-sublists-test
  (are [input output] (= output (group-sublists input))
       '(10 + 20 x 30)          '(10 + (20 x 30))
       '(1 + 2 + 3 - 4)         '(((1 + 2) + 3) - 4)
       '(123 + 456 x 789 - 876) '((123 + (456 x 789)) - 876)))

(deftest insert-parens-test
  (are [input output] (= output (insert-parens input))
       "10 + 20 x 30"           "10 + (20 x 30)"
       "1 + 2 + 3 - 4"          "((1 + 2) + 3) - 4"
       "123 + 456 x 789 - 876"  "(123 + (456 x 789)) - 876"))

; Helper function tests

(deftest index-test
  (is (= 0 (index first [:a :b] [:a :b :a :c :a])))
  (is (= 1 (index second '(:a :b) [:a :b :a :c :a])))
  (is (= 4 (index last #{:a :b} [:a :b :a :c :a])))
  (is (= nil (index last [:d :e] [:a :b :a :c :a]))))

