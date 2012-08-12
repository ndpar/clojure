(ns poker.core)

(defn hand-rank
  "Return a value indicating the ranking of a hand"
  [hand]
  nil)

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (max-key hand-rank hands))
