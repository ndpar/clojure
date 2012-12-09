;; http://en.wikipedia.org/wiki/Conway's_Game_of_Life
;;
;; Inspired at Global Day of Code Retreat 2012, Toronto

(ns dojo.life)

; http://clj-me.cgrand.net/2011/08/19/conways-game-of-life/

(defn neighbours [[x y]]
  (for [dx [-1 0 1] dy (if (zero? dx) [-1 1] [-1 0 1])]
    [(+ x dx) (+ y dy)]))

(defn next-step [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
             :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))
