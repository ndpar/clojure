(ns joy.astar-test
  (:use joy.astar clojure.test))

(def world [[  1   1   1   1   1]
            [999 998 999 999   1]
            [  1   1   1   1   1]
            [  1 999 999 999 999]
            [  1   1   1   1   1]])


(deftest auxiliary-tests
         (is (= '((0 1) (2 1) (1 0) (1 2))
                (map #(map + [1 1] %)
                     [[-1 0] [1 0] [0 -1] [0 1]])))
         (is (= (vec (replicate 5 (vec (replicate 5 nil))))
                (vec (repeat 5 (vec (repeat 5 nil))))
                [[nil nil nil nil nil]
                 [nil nil nil nil nil]
                 [nil nil nil nil nil]
                 [nil nil nil nil nil]
                 [nil nil nil nil nil]]))
         (is (= 998 (get-in world [1 1])))
         (is (= nil (get-in world [9 9])))
         (is (= [1 2] (keep identity [nil 1 nil 2])))
         (is (= [998] (keep #(get-in world %) [[1 1] [9 9]])))
         (is (= #{1 2 3 4} (into (sorted-set) (map inc (range 4)))))
         ;(is (= #{[901 [3 4]] [901 [4 3]]} ; throws ClassCastException
         ;       (into (sorted-set) '([901 '(4 3)] [901 '(3 4)]))))
         (is (= #{[901 [3 4]] [901 [4 3]]} ; vectors are comparable
                (into (sorted-set) '([901 [4 3]] [901 [3 4]])))))

(deftest neighbors-tests
         (is (= '([1 0] [0 1])
                (neighbors 5 [0 0]))))

(deftest estimate-cost-tests
         (is (= 80 (estimate-cost 10 5 0 0)))) ; 10 (5+5 - 0 - 0 - 2)

(deftest path-cost-tests
         (is (= 3  (path-cost 3 nil)))
         (is (= 3  (path-cost 3 'whatever)))
         (is (= 13 (path-cost 3 {:cost 10})))
         (is (= 13 (path-cost 3 {:cost 10 :yxs 'whatever}))))

(deftest total-cost-tests
         (is (= 93  (total-cost 13 10 5 0 0))) ; 13 + 80
         (is (= 1   (total-cost 1 900 5 4 4)))
         (is (= 901 (total-cost 1 900 5 4 3)))
         (is (= 901 (total-cost 1 900 5 3 4)))) ; symmetric

(deftest min-by-tests
         (is (= nil (min-by :cost [])))
         (is (= {:cost 9}
                (min-by :cost [{:cost 100} {:cost 36} {:cost 9}]))))

(deftest world-tests
         (is (= [{:cost 1, :yxs [[4 4]]} :steps 97]
                (astar [4 4] 900 world)))
         (is (= [{:cost 17,
                  :yxs [[0 0] [0 1] [0 2] [0 3] [0 4] [1 4]
                        [2 4] [2 3] [2 2] [2 1] [2 0] [3 0]
                        [4 0] [4 1] [4 2] [4 3] [4 4]]} :steps 94]
                (astar [0 0] 900 world))))


(def shrubbery [[1 1 1   2 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1   1 1]])

(deftest shrubbery-tests
         (is (= [{:cost 9,
                  :yxs [[0 0] [0 1] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [4 4]]} :steps 134]
                (astar [0 0] 900 shrubbery))))

(def bunny [[1 1 1   2 1]
            [1 1 1 999 1]
            [1 1 1 999 1]
            [1 1 1 999 1]
            [1 1 1 666 1]])

(deftest bunny-tests
         (is (= [{:cost 10,
                  :yxs [[0 0] [0 1] [0 2] [0 3] [0 4] [1 4] [2 4] [3 4] [4 4]]} :steps 132]
                (astar [0 0] 900 bunny))))

