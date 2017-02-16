# Welcome to Sonic Pi v2.11.1
cello_samples = "/Users/jshiverick/Documents/repos/musicbox/samples/cello/"

live_loop :cello do
  with_fx :reverb, room: 1 do
    sample cello_samples, 0, beat_stretch: 4, rate: 1, lpf: 130, amp: 2
  end
  sleep 4
end

live_loop :break  do
  with_fx :bitcrusher, bits: 6, cutoff: 80 do
    sample :loop_garzul, beat_stretch: 8, amp: 0, lpf: 80, rate: 0.5
  end
  sample :loop_garzul, beat_stretch: 8, amp: 1, lpf: 130, rate: 1
  sleep 8
end
