;; Clojure Programming, p. 146
;; https://github.com/clojurebook/ClojureProgramming/blob/master/ch03-collections-repl-interactions.clj

(ns cljprog.maze)

(defn maze
  "Returns a random maze carved out of walls; walls is a set of
  2-item sets #{a b} where a and b are locations.
  The returned maze is a set of the remaining walls."
  [walls]
  (let [paths (reduce (fn [index [a b]]
                        (merge-with into index {a [b] b [a]}))
                      {} (map seq walls))
        start-loc (rand-nth (keys paths))]
    ;(println "Start:" start-loc)
    (loop [walls walls
           unvisited (disj (set (keys paths)) start-loc)]
      ;(println "Unvisited:" unvisited)
      (if-let [loc (when-let [s (seq unvisited)] (rand-nth s))]
        (let [walk (iterate (comp rand-nth paths) loc)
              steps (zipmap (take-while unvisited walk) (next walk))]
          ;(println "Loc:" loc "Steps:" steps)
          (recur (reduce disj walls (map set steps))
                 (reduce disj unvisited (keys steps))))
        walls))))

(defn grid [w h]
  (set (concat
         (for [i (range (dec w)) j (range h)] #{[i j] [(inc i) j]})
         (for [i (range w) j (range (dec h))] #{[i j] [i (inc j)]}))))

(defn draw [w h maze]
  (doto (javax.swing.JFrame. "Maze")
    (.setContentPane
      (doto (proxy [javax.swing.JPanel] []
              (paintComponent [^java.awt.Graphics g]
                (let [g (doto ^java.awt.Graphics2D (.create g)
                          (.scale 10 10)
                          (.translate 1.5 1.5)
                          (.setStroke (java.awt.BasicStroke. 0.4)))]
                  (.drawRect g -1 -1 w h)
                  (doseq [[[xa ya] [xb yb]] (map sort maze)]
                    (let [[xc yc] (if (= xa xb)
                                    [(dec xa) ya]
                                    [xa (dec ya)])]
                      (.drawLine g xa ya xc yc))))))
        (.setPreferredSize (java.awt.Dimension. (* 10 (inc w)) (* 10 (inc h))))))
    .pack
    (.setVisible true)))

; (ns cljprog.maze)
; (load "maze")
; (draw 3 3 #{#{[1 0] [1 1]} #{[2 1] [1 1]} #{[0 0] [0 1]} #{[1 1] [1 2]}})
; (draw 40 40 (maze (grid 40 40)))
