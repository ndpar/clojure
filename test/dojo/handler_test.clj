(ns dojo.handler-test
  (:use clojure.test dojo.handler))

(deftest on-message-test
  (is (= [] (on-message {:id 1})))
  (is (= ["Document:A:null" "Alert:A:null"] (on-message {:id 1 :a "A"})))
  (is (= ["Note:B:null" "Alert:null:B"] (on-message {:id 1 :b "B"})))
  (is (= ["Document:A:null" "Note:B:null" "Alert:A:B"] (on-message {:id 1 :a "A" :b "B"})))
  (is (= ["Document:A:C" "Note:null:C" "Alert:A:null"] (on-message {:id 2 :a "A" :c "C"})))
  (is (= ["Document:A:C" "Note:B:C" "Alert:A:B"] (on-message {:id 2 :a "A" :b "B" :c "C"}))))

(deftest var-capturing-test
  (let [msg-handler (handler msg [msg something]
                             (format "Msg:%s:%s" msg something))
        x-handler (handler x [x something]
                           (format "X:%s:%s" x something))]
    (is (= "Msg:3:5" (msg-handler {:msg 3 :something 5})))
    (is (= "X:3:5" (x-handler {:x 3 :something 5})))))
