(ns dojo.parens-test
  (:use clojure.test dojo.parens))

(deftest group-list-test
         (is (= '(10 + (20 x 30) + 40 x 50) (group-list '(10 + 20 x 30 + 40 x 50) 3))))

(deftest last-operation-index-test
         (is (= 3 (last-operation-index '(10 + 20 x 30 + (40 x 50)))))
         (is (= 7 (last-operation-index '(10 + 20 x 30 + 40 x 50))))
         (is (= nil (last-operation-index '(10 + 20 + 30 + 40 + 50)))))

(deftest first-operation-index-test
         (is (= 3 (first-operation-index '((10 + 20) x 30 + 40 x 50))))
         (is (= 1 (first-operation-index '(10 - 20 x 30 + 40 x 50))))
         (is (= nil (first-operation-index '(10 x 20 x 30 x 40 x 50)))))

(deftest group-right-assoc-test
         (is (= '(10 + (20 x 30) + (40 x 50)) (group-right-assoc '(10 + 20 x 30 + 40 x 50))))
         (is (= '(10 + (20 x (30 x (40 x 50)))) (group-right-assoc '(10 + 20 x 30 x 40 x 50)))))

(deftest group-left-assoc-test
         (is (= '((10 + 20) x (30 + 40) x 50) (group-left-assoc '(10 + 20 x 30 + 40 x 50))))
         (is (= '((((10 + 20) + 30) + 40) x 50) (group-left-assoc '(10 + 20 + 30 + 40 x 50)))))

(deftest group-sublists-test
         (is (= '(10 + (20 x 30)) (group-sublists '(10 + 20 x 30))))
         (is (= '(((1 + 2) + 3) - 4) (group-sublists '(1 + 2 + 3 - 4))))
         (is (= '((123 + (456 x 789)) - 876) (group-sublists '(123 + 456 x 789 - 876)))))

(deftest insert-parens-test
         (is (= "10 + (20 x 30)" (insert-parens "10 + 20 x 30")))
         (is (= "((1 + 2) + 3) - 4" (insert-parens "1 + 2 + 3 - 4")))
         (is (= "(123 + (456 x 789)) - 876" (insert-parens "123 + 456 x 789 - 876"))))

