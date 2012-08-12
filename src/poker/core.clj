(ns poker.core)

(defn card-ranks
  "TODO"
  [hand]
  nil)

(defn straight?
  "TODO"
  [ranks]
  nil)

(defn flush?
  "TODO"
  [hand]
  nil)

(defn kind
  "TODO"
  [n ranks]
  0)

(defn two-pair
  "TODO"
  [ranks]
  0)

(defn hand-rank
  "Return a value indicating the ranking of a hand"
  [hand]
  (let [ranks (card-ranks hand)]
    (cond
      (and (straight? ranks) (flush? hand)) [8 (max ranks)]
      (< 0 (kind 4 ranks)) [7 (kind 4 ranks) (kind 1 ranks)]
      (and (< 0 (kind 3 ranks)) (< 0 (kind 2 ranks))) [6 (kind 3 ranks) (kind 2 ranks)]
      (flush? hand) [5 (card-ranks hand)]
      (straight? ranks) [4 (max ranks)]
      (< 0 (kind 3 ranks)) [3 (kind 3 ranks) (card-ranks hand)]
      (< 0 (two-pair ranks)) [2 (two-pair ranks) (card-ranks hand)]
      (< 0 (kind 2 ranks)) [1 (kind 2 ranks) (card-ranks hand)]
      :else [0 (card-ranks hand)])))

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (max-key hand-rank hands))
