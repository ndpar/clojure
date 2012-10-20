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
         (is (= '(10 + (20 x 30)) (group-sublists '(10 + 20 x 30))))
         (is (= '(((1 + 2) + 3) - 4) (group-sublists '(1 + 2 + 3 - 4))))
         (is (= '((123 + (456 x 789)) - 876) (group-sublists '(123 + 456 x 789 - 876)))))

(deftest insert-parens-test
         (is (= "10 + (20 x 30)" (insert-parens "10 + 20 x 30")))
         (is (= "((1 + 2) + 3) - 4" (insert-parens "1 + 2 + 3 - 4")))
         (is (= "(123 + (456 x 789)) - 876" (insert-parens "123 + 456 x 789 - 876"))))

; Helper function tests

(deftest index-test
         (is (= 0 (index first [:a :b] [:a :b :a :c :a])))
         (is (= 1 (index second '(:a :b) [:a :b :a :c :a])))
         (is (= 4 (index last #{:a :b} [:a :b :a :c :a])))
         (is (= nil (index last [:d :e] [:a :b :a :c :a]))))

