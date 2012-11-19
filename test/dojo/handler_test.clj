(ns dojo.handler-test
  (:use clojure.test dojo.handler))

(deftest on-message-test
  (is (= [] (on-message {:id 1})))
  (is (= ["Document:A:null" "Alert:A:null"] (on-message {:id 1 :a "A"})))
  (is (= ["Note:B:null" "Alert:null:B"] (on-message {:id 1 :b "B"})))
  (is (= ["Document:A:null" "Note:B:null" "Alert:A:B"] (on-message {:id 1 :a "A" :b "B"})))
  (is (= ["Document:A:C" "Note:null:C" "Alert:A:null"] (on-message {:id 2 :a "A" :c "C"})))
  (is (= ["Document:A:C" "Note:B:C" "Alert:A:B"] (on-message {:id 2 :a "A" :b "B" :c "C"}))))
