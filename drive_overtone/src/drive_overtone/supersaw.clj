(ns drive-overtone.supersaw)
(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)

(definst supersaws [freq 440 dur 0.2 release 0.5 amp 5.6 cutoff 3500 env-amount 0.5]
         (let [snd-fn (fn [freq]
                        (let [tune (ranged-rand 0.99 1.01)]
                          (-> (lf-saw (* freq tune))
                              (delay-c 0.005 (ranged-rand 0.0001 0.01)))))
               hi-saws (splay (repeatedly 5 #(snd-fn freq)))
               lo-saws (splay (repeatedly 5 #(snd-fn (/ freq 2))))
               noise (pink-noise)
               snd (+ (* 0.65 hi-saws) (* 0.85 lo-saws) (* 0.12 noise))
               env (env-gen (adsr 0.001 0.7 0.2 0.1) (line:kr 1 0 (+ dur release)) :action FREE)]
           (-> snd
               (clip2 0.45)
               (rlpf (+ freq (env-gen (adsr 0.001) (line:kr 1 0 dur) :level-scale cutoff)) 0.75)
               (free-verb :room 1.8 :mix 0.45)
               (* env amp)
               pan2)))

(event-debug-off)

(midi->hz 63 )
(println "hey")

(on-event [:midi :note-on]
                (fn [e]
                  (let [note  (:note e)
                     vel  (:velocity-f e)]
                         (supersaws :freq (midi->hz note))))
                  ::keyboard-handler)
(stop)
(demo (saw 440.0))
(demo (saw 440.0))

(demo (supersaw :freq 440 :dur 2 :amp 1))
