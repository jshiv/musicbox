# Welcome to Sonic Pi v2.11.1
use_random_seed 3
notes = (scale :e1, :minor_pentatonic, num_octaves: 3).shuffle


live_loop :beads do
  tick_reset
  t = 0.04
  with_fx :bitcrusher do
    s = synth :dsaw, note: :e3, sustain: 8, note_slide: t, release: 0, amp: 0.8, cutoff: 80
    64.times do
      sleep 0.125
      control s, note: notes.tick
    end
  end
  sleep t
end


live_loop :industrial do
  sample :loop_industrial, beat_stretch: 1, lpf: 120
  sleep 1
end

live_loop :drive do
  sample :bd_haus, amp: 5, lpf: 70, beat_stretch: 0.5
  sleep 0.5
end