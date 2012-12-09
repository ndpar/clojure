(ns dojo.life-test
  (:use clojure.test dojo.life))

(deftest neighbours-test
  (is (= [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]]
         (neighbours [2 2]))))

(deftest blinker-test
  (is (= #{[2 3] [3 3] [4 3]}
         (next-step #{[3 2] [3 3] [3 4]})))
  (is (= #{[3 2] [3 3] [3 4]}
         (next-step #{[2 3] [3 3] [4 3]}))))

(deftest beehive-test
  (is (= #{[3 2] [2 3] [2 4] [3 5] [4 4] [4 3]}
         (next-step #{[3 2] [2 3] [2 4] [3 5] [4 4] [4 3]}))))
