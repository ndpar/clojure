(ns cljprog.hobbit-test
  (:use clojure.test
        cljprog.hobbit))

(deftest battle-test
  (def smaug (character "Smaug" :health 500 :strength 400))
  (def bilbo (character "Bilbo" :health 100 :strength 100))
  (wait-futures 1
                (play bilbo attack smaug)
                (play smaug attack bilbo))
  ; Smaug kills Bilbo
  (is (= 0 (:health @bilbo))
      (pos? (:health @smaug))))

(deftest epic-battle-test
  (def smaug (character "Smaug" :health 500 :strength 400))
  (def bilbo (character "Bilbo" :health 100 :strength 100))
  (def gandalf (character "Gandalf" :health 75 :mana 1000))
  (wait-futures 1
                (play bilbo attack smaug)
                (play smaug attack bilbo)
                (play gandalf heal bilbo))
  ; with Gandalf's help Bilbo kills Smaug
  (is (= 0 (:health @smaug))
      (pos? (:health @bilbo))))

(deftest loot-test
  (def smaug (character "Smaug" :items (set (range 50))))
  (def bilbo (character "Bilbo"))
  (def gandalf (character "Gandalf"))
  (wait-futures 1
                (while (loot smaug bilbo))
                (while (loot smaug gandalf)))
  ; Overlap in Bilbo's and Gandalf's items is always empty
  (is (= [] (filter (:items @bilbo) (:items @gandalf)))
      (= 0 (:items @smaug))))
