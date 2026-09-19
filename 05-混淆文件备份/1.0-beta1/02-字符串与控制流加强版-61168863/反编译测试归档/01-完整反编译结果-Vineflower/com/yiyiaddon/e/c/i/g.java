package com.yiyiaddon.e.c.i;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;

public final class g implements e {
   private static final int bo = 2;
   private static final int bp = 2;
   private final com.yiyiaddon.e.c.d.d a;
   private final com.yiyiaddon.e.c.c.h f;
   private final double m;
   private boolean N;
   private int aX;
   private int aY;
   private final f b = new f();
   private boolean O;

   public g(com.yiyiaddon.e.c.d.d var1, com.yiyiaddon.e.c.c.h var2, double var3) {
      this.a = var1;
      this.f = var2;
      this.m = var3;
   }

   public com.yiyiaddon.e.c.d.d c() {
      return this.a;
   }

   @Override
   public l a() {
      if (!this.N) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dop7pqd9c39v","PIuPt4Xi0DdnGClYjIGNMPMBqXqCZQ0GNGzN/YmwiJQ=",-3806746727979230692,6357972316424747571,7088739113230239134,2565930440092296728>()) {
            case -939394518:
               if (!this.f.d(this.a)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2lyukoyvbuxxr","LPADLpD6vZc+qmcMsmnDiyqGVNLyxUNVwfRSQn0V2RQ=",-4733932765859714989,8737664561454983090,-5172179075924221463,-4591771043461757026>()) {
                     case -812319796:
                        return l.TARGET_INVALID;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (!this.R()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lelxwgsv8l4s","4ESFpVqUVFaIQ6jgJuB0cFYC7Q3fERRDPdEZCWNtLkU=",-7377569289583353397,5771295323929654202,-7245373164965112277,-4580149754951674381>()) {
            case -1560154469:
               if (!com.yiyiaddon.i.c.a.ft()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11222epn1hfy3","91WtJuwR5u+zgN1CNhCo2u5YIXOyClPwvhv7BYa6S+0=",7773091022051052941,-5764535964275793549,-4827490687930217382,-3385720562279988872>()) {
                     case -1549622076:
                        return l.NAVIGATION_FAILED;
                     default:
                        throw null;
                  }
               } else {
                  if (!com.yiyiaddon.i.c.a.cX()) {
                     switch ((int)com.yiyiaddon.m.b.a<"simkhvldg5jgx","O6sQZZ/86LnlQEVMTrk3Xwc4LYjanQTQWAtswtPM9Fs=",-3593915313124448303,2429642822772123729,-3851223558709298271,5028537625964204585>()) {
                        case 997560304:
                           com.yiyiaddon.i.c.a.b(this.a.a(), 1);
                           switch ((int)com.yiyiaddon.m.b.a<"sqnfdxhywa462","+O9A9Tbw2ELkE2RadNuMNA7Q33X78SKyBoW6whUxku0=",-3004543582276073184,-8837057379767269307,8307273218468899751,-6523139054346892324>()) {
                              case 1346520284:
                                 return l.IN_PROGRESS;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return l.IN_PROGRESS;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.i.c.a.i();
         if (!this.N) {
            switch ((int)com.yiyiaddon.m.b.a<"s2os9xv16xpg82","ar26VRb9YJ2xBhzl2B6kuMs9WXZfSYJLLhVqV66eRS0=",5451457532493709874,7270796471500455324,7403961617055729796,-2633265433050309662>()) {
               case 1715701846:
                  if (!this.O) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ymmcplq7byji","gl3zZEfuqVsK1jdpqxNX8Q/pxkzxcWYnAnFphi9eInA=",-8168818718801897697,1636101458947616180,7943345659028843938,-6288836755682228708>()) {
                        case 1654400394:
                           this.O = this.b.Q();
                           if (!this.O) {
                              switch ((int)com.yiyiaddon.m.b.a<"s262cmxunxkdcx","t4qXEKa9NS+p+dbru9Dl9C83zKEvag8Ja8lyLWY2qLk=",-6448776728650592340,2193124199365849216,-3957163358185615953,-7799793085570121722>()) {
                                 case -1655394732:
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (!this.N) {
            switch ((int)com.yiyiaddon.m.b.a<"s1q34r7e5mc6uo","9zPSUm6EHu7K4Gp2AnIbxBplumhsj9Hp5TI+E0ePvtU=",784779604433146726,-6331102030042232787,-2657542456786675208,-7219455638237248848>()) {
               case -200802235:
                  com.yiyiaddon.i.d.a.d(this.a.a(), Direction.UP);
                  this.N = true;
                  this.aX = 0;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else if (this.aX < 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3m7awxv3qxxc3","1RwoMVZRho0bo7UIhPbXgXf6avAlAyqT9y+VMNbXf7U=",-2791696428629038310,6984818576539872672,6978499019284957571,-24935851890246389>()) {
               case 198855341:
                  this.aX++;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else if (this.f.a(this.a)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jre24otcw7ah","zYY8sscWd85skBEDnkWH4eDI/s4iSUqX6MVNWcViiSk=",1725910736773185232,-604058571551029107,401209507873029726,8773611554462828874>()) {
               case -283168237:
                  return l.SUCCESS;
               default:
                  throw null;
            }
         } else if (this.aY < 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s14zfoqrzet105","tbAWdYbiGeE3cYH6UtSO0zSyygGuL2sKY+8w8FIcCAw=",-8116516237589191557,319842443227083267,4245882605652893210,-6530533720972530239>()) {
               case 225892501:
                  this.aY++;
                  this.N = false;
                  this.aX = 0;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else {
            return l.HARVEST_FAILED;
         }
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
      this.b.ax();
   }

   private boolean R() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hk38ltnvqmbz","JBEJOsBI+HP+SjWElujOehroTDd9K2Z6RI5WnwokmRU=",3349013183689725665,-6360145559006022717,-4779724930017968573,7434479666261022960>()) {
            case -777958304:
               return false;
            default:
               throw null;
         }
      } else {
         double var2 = this.a.a().getX() + 0.5 - var1.player.getX();
         double var4 = this.a.a().getY() + 0.5 - var1.player.getEyeY();
         double var6 = this.a.a().getZ() + 0.5 - var1.player.getZ();
         if (var2 * var2 + var4 * var4 + var6 * var6 <= this.m * this.m) {
            switch ((int)com.yiyiaddon.m.b.a<"s33hzmnz28hz7c","rWvvQTIdwnjZNo48SPw46CStSB/XwYQu5pd/hJP/kh4=",-111499586914102640,1647021536537111467,-2496273603238631880,9186710323052543737>()) {
               case -1932201508:
                  switch ((int)com.yiyiaddon.m.b.a<"s8rxb2h1bcboz","t7YpuCJS6TS8952BVevnMXcrNuP35GiNCLKy8z2rO6g=",-6069557208171982690,-2415760456517432864,-1899926027842643251,1485976357532865027>()) {
                     case -1331806930:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"sm6sd7igo9kz4","lEgfvnNH2us0arYExrvoiap8ovbJwEleiBQ/vispuxw=",-8458896836094639466,9016192403255097436,-7127572692830365659,-962937179725317265>()) {
               case 386066030:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }
}
