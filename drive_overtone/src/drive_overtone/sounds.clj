(ns drive-overtone.sounds)
(use 'overtone.live)
(use 'overtone.inst.sampled-piano)


(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))
(sin-wave :freq 220)


(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))
(saw-wave)

(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse:ar freq)
     vol))
(square-wave)

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))
(noisey)

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-tri freq)
     vol))
(triangle-wave)



(defsynth dark-sea-horns
  "Dark, rough and sharp sea horn.
   Without any attempt to use recursion (recursive example lacked the feedback feel)"
  [out-bus 0 amp 1 freq 65]
  (let [a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* (lf-noise1:ar 0.1) 3))))

        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)

        a (tanh (* 6 (lf-noise1:ar 3) (sin-osc:ar freq (* a (lf-noise1:ar 0.1) 3))))
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (allpass-l:ar a 0.3 [(+ (ranged-rand 0 0.2) 0.1) (+ (ranged-rand 0 0.2) 0.1)] 5)
        a (tanh a)]
    (out out-bus (* amp [ a a]))))
