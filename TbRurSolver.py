from sympy import solve, Eq, Symbol
import numpy as np
import sys

if (len(sys.argv) > 1):
  #print (sys.argv)
  i=int(sys.argv[1])
  dz=float(sys.argv[2])
  ref_ta = float(sys.argv[3])
  UTb = float(sys.argv[4])
  mod_U_TaRef = np.zeros((i+1,1))
  mod_U_TaRef[i] = float(sys.argv[5])
  Ri_rur = float(sys.argv[6])

###### Solve Richardson's number eq for "high temperature" aka Tb_rur 
#Tb_rur_temp=0.0
Thi_tb = Symbol('Thi_tb')


try:
    Tb_rur = solve(9.806*dz*(Thi_tb-ref_ta)*2.0/(Thi_tb+ref_ta)/(UTb-mod_U_TaRef[i])**2.0-Ri_rur, Thi_tb,quick=False)[0]    
    #Tb_rur_prev = Tb_rur
except ValueError:
    print (-9999.)   
#Tb_rur = Tb_rur_prev   
#Tb_rur_temp=Tb_rur
#Tb_rur = Tb_rur - 9.806/1004.67*dz
print (Tb_rur)
