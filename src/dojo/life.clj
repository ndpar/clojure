;; http://en.wikipedia.org/wiki/Conway's_Game_of_Life
;;
;; Inspired at Global Day of Code Retreat 2012, Toronto

(ns dojo.life)

; http://rosettacode.org/wiki/Conway's_Game_of_Life#Clojure

(defn neighbours [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1]
        :when (not (and (zero? dx) (zero? dy)))]
    [(+ x dx) (+ y dy)]))

(defn next-step [cells]
  (set (for [[cell n] (frequencies (mapcat neighbours cells))
             :when (or (= n 3) (and (= n 2) (some #{cell} cells)))]
         cell)))
