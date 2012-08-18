# Poker hand evaluator

A Clojure library designed to compare hands of 5-card Poker game.
The main idea of hand comparison is taken from Peter Norvig's class
[Design of Computer Programs][http://www.youtube.com/playlist?list=PL818D7B4539EED6D3].
`src/poker/core.clj` is in fact a Clojure port of Norvig's program written in Python.

Two hands are compared by hand rank. A hand rank is a tuple with the first
element an integer from the range 0..8 defining the type of the hand: 8 is
straight flush, 7 is four-of-a-kind, and so on.
Second and (optional) third elements of the tuple are used to compare two
hands of the same type. For example, [8 14] is Royal flush and it beats
[8 10] which is straight flush ten high.

## Usage

To run test cases you need [Leiningen][http://leiningen.org] installed

    lein test

## License

Copyright © 2012 Andy Paramonov

Distributed under the Apache License, Version 2.0.
