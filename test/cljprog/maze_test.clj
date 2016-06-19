(ns cljprog.maze-test
  (:use clojure.test cljprog.maze))

(deftest merge-test
  (is (= {:c [1] :a {1 2} :b [4]}
         (merge {:a {0 1} :b [2 3]}
                {:a {1 2} :b [4]}
                {:c [1]}))))

(deftest into-test
  (is (= #{1 2 3 4}
         (into #{1 2 3} #{2 3 4})))
  (is (= [1 2 3 4]
         (into [1 2] [3 4])))
  (is (= '(4 3 1 2)
         (into '(1 2) '(3 4)))))

(deftest merge-with-test
  (is (= {:c [1] :a {0 1 1 2} :b [2 3 4]}
         (merge-with into
                     {:a {0 1} :b [2 3]}
                     {:a {1 2} :b [4]}
                     {:c [1]}))))

(deftest seq-test
  (let [[a b] '(1 2)]
    (is (and (= a 1) (= b 2))))
  (is (= '(([1 1] [0 1]) ([1 1] [1 2]))
         (map seq #{#{[0 1] [1 1]} #{[1 1] [1 2]}}))))      ; seq of seqs

#_(deftest walk-test
  (let [paths {[0 0] [[1 0] [0 1]]
               [1 0] [[0 0] [1 1]]
               [0 1] [[0 0] [1 1]]
               [1 1] [[0 1] [1 0]]}]
    (print (take 5 (iterate (comp rand-nth paths) [0 0])))))

(deftest zipmap-test
  (let [numbers (iterate inc 0)
        pairs (zipmap (take-while #(< % 10) numbers) (next numbers))]
    (is (= pairs {0 1, 1 2, 2 3, 3 4, 4 5, 5 6, 6 7, 7 8, 8 9, 9 10}))))

(deftest map-set-test
  (is (= '(#{1 2} #{3 4})
         (map set {1 2 3 4}))))

#_(deftest maze-test
  (println (maze (grid 5 5))))

(deftest hex-grid-test
  (is (= (hex-grid 3 3)
         #{#{[0 0] [2 0]} #{[2 0] [4 0]}
           #{[0 0] [1 1]} #{[1 1] [2 0]} #{[2 0] [3 1]} #{[3 1] [4 0]} #{[4 0] [5 1]}
           #{[1 1] [3 1]} #{[3 1] [5 1]}
           #{[0 2] [1 1]} #{[1 1] [2 2]} #{[2 2] [3 1]} #{[3 1] [4 2]} #{[4 2] [5 1]}
           #{[0 2] [2 2]} #{[2 2] [4 2]}}))
  (is (= (hex-grid 2 2)
         #{#{[0 0] [2 0]}
           #{[0 0] [1 1]} #{[1 1] [2 0]} #{[2 0] [3 1]}
           #{[1 1] [3 1]}})))