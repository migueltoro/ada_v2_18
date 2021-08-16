\ LP format example
Minimize
  
  x0 + x1 + x2 +x3

Subject To
  
  sc0: x0 - x1 + r0 = 0
  sc1: x0 - x2 - r1 = 0
  sc2: x1 - x2 - r2 = 0
  sc3: x0 - x3 - r3 = 0
  sc4: x2 - x3 - r4 = 0
  sd0: x0 - x1 + 1 - u0 = 0
  sd1: x0 - x2 -2 - u1 = 0
  sd2: x1 - x2 + 1 - u2 = 0
  ss0: x0 - x1 + -1 - v0 = 0
  ss1: x0 - x2 - 2 - v1 = 0
  ss2: x1 - x2 - 1 - v2 = 0

Bounds
  
  0 <= x0 <= 3
  0 <= x1 <= 3
  0 <= x2 <= 3
  0 <= x3 <= 3
  ar0 >= 0.1
  ar1 >= 0.1
  ar2 >= 0.1
  ar3 >= 0.1
  ar4 >= 0.1
  au0 >= 0.1
  au1 >= 0.1
  au2 >= 0.1
  av0 >= 0.1
  av1 >= 0.1
  av2 >= 0.1
  r0 free
  
  
Generals
 
  x0 x1 x2 x3
  
Semi-continuous

  ar0

General Constraints
  
  c0: ar0 = ABS ( r0 )
  c1: ar1 = ABS ( r1 )
  c2: ar2 = ABS ( r2 )
  c3: ar3 = ABS ( r3 )
  c4: ar4 = ABS ( r4 )
  d0: au0 = ABS ( u0 )
  d1: au1 = ABS ( u1 )
  d2: au2 = ABS ( u2 )
  s0: av0 = ABS ( v0 )
  s1: av1 = ABS ( v1 )
  s2: av2 = ABS ( v2 )


End