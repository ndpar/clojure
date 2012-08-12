(ns poker.core)

(defn hand-rank
  "TODO"
  []
  nil)

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (max-key hand-rank hands))
