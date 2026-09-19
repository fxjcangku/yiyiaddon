package com.yiyiaddon.e.n.p;

import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.s;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class d {
   public static final int mY = 5;
   private static final int mZ = 256;
   private static final Map<String, com.yiyiaddon.g.d.a> an = new ConcurrentHashMap<>();

   private d() {
   }

   public static boolean a(BlockPos var0, p var1, Collection<String> var2) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1eedq42ludmy7","WRMJssI1/bt3u7jnM2cX9IFrRuba3X433TDqkXo/s+M=",5959116436402977308,-2575692662193415862,5498108408999681024,-976012980153569013>()) {
            case -478225067:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3p36if2sdcuzt","heT07HZ98l52U2N0lr9pLK+FZRt8DbI13vhgrHbEZh0=",4555057901040278036,491264826793228823,9061914954111354716,740110205651049750>()) {
                     case 522605895:
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sswkjn053mxwk","fP58s+1eaiJDSt4Kf7wjq4hP+9kcq5byyoYVePytUUY=",5889595399153274231,2364042968352452521,-8845755662886122994,5969574623643077040>()) {
                              case 1027318964:
                                 if (!var2.isEmpty()) {
                                    if (!com.yiyiaddon.k.e.c.fr()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2a0xidx5yzo1d","Vrw1L+J+MH7rvnm+jcxrewDHSHRD1MbCjPPtamaEIQE=",-5656671487531069401,-8220302072363064375,-874361514424156877,-5786908998207676648>()) {
                                          case -446076985:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    ClientLevel var3 = Minecraft.getInstance().level;
                                    if (var3 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"skrzxm4czjmxk","G6tErvy5jK/jdD/aPLw+DPD0Tig3cjdYRJnCwZGyLF0=",4869327049144738388,-2543941800598613878,-4568143541743100594,-8901530359126871679>()) {
                                          case 2019621606:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    int var4 = 1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s3u2s7ni3hkepk","CmiQ7JDMytuTMW9/GJPls53yG8I7QKAngSK5PtKj1So=",-7859057745166183730,-7815239955202327712,3197217786404826132,-2231262266291428365>()) {
                                       case -778061399:
                                          while (var4 <= 5) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1vh6anb5hn4sb","wdPIVE+8dHr6nE0vFFtUN3IbaOM6ZQKc1u6joytehkI=",-6745981529052093198,-5827870106075592691,-2062631853628695399,4553316565176187549>()) {
                                                case 2543256:
                                                   BlockPos var5 = var0.above(var4);
                                                   if (!var3.isLoaded(var5)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1v2c4hvtp0w8","9GriFlcZ+ehMie6z4B79v0pEAL+POghQapB6g4nu4q8=",4164565988873065328,-1745546165208256015,5807681223111718622,-8250507059835798482>()) {
                                                         case -987719649:
                                                            return false;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   BlockState var6 = var3.getBlockState(var5);
                                                   if (var6.isAir()) {
                                                      label57:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1bcwavxn9p4ol","otN9oOM2xAw5pNyFhLxMZGq2pBnAEe9QXTy1qZ7tY20=",-8230124126939799390,6497135690146705335,-5100653831684266384,520155393379991886>()) {
                                                         case 384910848:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s22ejlfpa1r5b9","TEgto7T7KkTgzr8geEsXZzs7wCpclFfO6sHe2XcPjn4=",-2786504011100689439,8929467285639389120,7980465943120395027,-2299092555715633852>()) {
                                                               case -1956860104:
                                                                  break label57;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else if (a(a(var6), var1, var2)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s20vu0b7sv5cv0","UpHD3hMGCHUHZVWeeFYFyJB0JSfKeP/8yqQ7n7FHdd8=",-6070244171773104054,5872632154405310793,2521252842735168455,-7781540566384332026>()) {
                                                         case -1784113371:
                                                            return true;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   var4++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s31n753r3cd726","NdtClNtOaBFC1tiS5Cefb8ijRQAmAJGSxir4yoUYZjU=",-7387217794870913097,8159665742904946481,1873084867667730362,9082956240836032428>()) {
                                                      case 1892311385:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3jl1g09v3qudv","Ha9ba9kNGFFB2GZFdsWHIAWWUpUdVtAqAf+AwtWcxKU=",626493137319719128,-6789184201997187909,-961715948513882679,-2685436048769846103>()) {
                                    case 1933537385:
                                       return false;
                                    default:
                                       throw null;
                                 }
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

      return false;
   }

   private static com.yiyiaddon.g.d.a a(BlockState var0) {
      String var1 = com.yiyiaddon.k.e.c.dn() + var0;
      com.yiyiaddon.g.d.a var2 = an.get(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11em0z9pa7qbr","4VCV/+IIsVhCrFe518nhLz/zmhIW5nkbiELYXrdsxAY=",9063896725273269184,-6612395418381992760,-9192887515852329121,-9205814087579665470>()) {
            case 1216434727:
               return var2;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.d.a var3 = com.yiyiaddon.i.e.a.b(var0);
         if (an.size() >= 256) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"suvbl9q66fppi","OA552JJvnO18m1o1Y+cq5AZRyIRiLYWls+mufyDZiqQ=",907867198606807565,4091139130477072850,-7722365416528927747,-4255748848189677071>()) {
               case 264145033:
                  an.clear();
                  switch ((int)com.yiyiaddon.m.b.a<"s2cwc1yz1y52cu","s6UTqXVI+Fyba6AA/5OXxpngQp9O5ROQCkIdqQ0BQM8=",6976806276901160518,6551212393975272594,-7924848756984208623,6613368882443860079>()) {
                     case -382648286:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         an.put(var1, var3);
         return var3;
      }
   }

   private static boolean a(com.yiyiaddon.g.d.a var0, p var1, Collection<String> var2) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sikt1w2xfo8ez","nZg9ozgSdWVQS9nNv4pFXZjTynVTmRjLzR3IgkdU9ws=",-8942255484346104093,1078020258712044717,-7231831305716339983,-8508043616734830237>()) {
            case -1699119636:
               return false;
            default:
               throw null;
         }
      } else {
         ArrayList var3 = new ArrayList();
         if (var0.ea() != null) {
            label91:
            switch ((int)com.yiyiaddon.m.b.a<"s1g8b17pj9zd77","vD43UfeXa2ENTOZI1EcXbSnWAeGFnhPkruOQNrXPbN4=",7853590663997041914,-4184826231291274250,-880204153933016469,-2652718084787016698>()) {
               case -633824227:
                  String[] var4 = var0.ea()
                     .split(
                        (String)com.yiyiaddon.m.b.a<"s1xm8zv4h8236a","NSya+f1kCkOlJ+wXJ1hxMJejq1clXeVXNy2kE/eZuz8=",1676334944278464311,4608238724835259429,2070723520405919342,4578875079286506623>()
                     );
                  int var5 = var4.length;
                  int var6 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s294vjlviv32s9","/1VuX06xBodq2WRVVM1C1yK67bc8jitm/KNYjfCS3Fc=",7121079948466795641,8834703425178849362,-6317259758926398676,7685302501711772002>()) {
                     case -866651365:
                        while (var6 < var5) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3lkk1e3ogxqdd","atKAR+jAvg7cp0y5BAUU/NLY5cZOvAdouYXiZTmdmCE=",-3937786716375382211,701523679913323225,-630470063291852874,4614476053869845484>()) {
                              case -1083732038:
                                 String var7 = var4[var6];
                                 var3.add(var7.trim());
                                 var6++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s277tjpzp4lmdr","yaQdAu5dL9naV6L3MV3ik1JkeSwtQqoyc+6an5bj31Y=",-5545903376909931669,5800645590425088015,2771786301742651645,-8761969059415946470>()) {
                                    case -385663918:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label91;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Iterator var8 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1cp4loxfydilo","ofngbk/lmljTKzxSEvWejfLSUO/gVSR3wFSTnTCw5bM=",-5298105302710532614,-3364819970502131941,5170040159350023546,8793394925534519423>()) {
            case 1805069101:
               while (var8.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qe4kt1o2qx7k","lK/R3bS6R2XTmsB+N9BBm/5GHLeyYlisSRF1kgMdVTU=",8331441640267617008,3940685860026545996,-7749116132645891554,-5851876248815052161>()) {
                     case 306085342:
                        String var9 = (String)var8.next();
                        s var11 = var1.a(var9);
                        if (var11 instanceof com.yiyiaddon.e.n.i.d) {
                           switch ((int)com.yiyiaddon.m.b.a<"s21vkty79g0bvl","35HUknFK6kify3Nc2VdGJa93ZMf9BFnvEA58iONbs/A=",7618225221347762078,-5878935277669562123,3873411133250644194,4241487360100752308>()) {
                              case 1388510329:
                                 com.yiyiaddon.e.n.i.d var10 = (com.yiyiaddon.e.n.i.d)var11;
                                 if (var0.dv() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s12xbcumur8kcp","g6VgGohsPgBZQKejCXWb9OwTNHGvjs85ANov9q4A1EQ=",-1722869373410834391,-7587051363078208991,1037921385056953855,-37594149735695734>()) {
                                       case 1166034631:
                                          if (var0.dv().equals(var10.dF())) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s912io6y2mhoz","AmPnirkIU9zmFrD2ApnfpcDprs4eX1y73cXmH/B/zho=",3608897467502003124,4442198197385998681,8261337477690595629,-7517742339539031925>()) {
                                                case -409150281:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var10.dJ() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1pxgrn4zfnhvm","lP51ry3dyELh6URsnGSz8/gNfRRBhtEBDVpINNvvIKA=",595694553425337324,2501923563737206185,631714771557911671,-3728007188049126890>()) {
                                       case 1763933718:
                                          if (var3.contains(var10.dJ())) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1o7au9wotozri","ymiZ6KxU6A7LI96pMByLy5h1Ek1z9nFgNw3qhH99QHA=",-2319081317779912936,-3009860090470838323,6684256867101014417,38134008014816215>()) {
                                                case -89078998:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var10.dE() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1kjjkij95cz6p","ZHws7iIMH0tZ9XiGuGUwhUkSgpkBD9tgY51zHJQ3QyU=",-9019808838599243039,-3391792842197999207,6931764184457913707,-4746640818390557270>()) {
                                       case -1121079974:
                                          if (var3.contains(var10.dE())) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3bz227w0g1i71","tLuI7CYaqGT4BwDpwCZhbVxeBNKupSLzTVJMTasEOGE=",3697374660623058362,4528641195771841767,-8511207502974215792,7586145022398679418>()) {
                                                case -400615467:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1f3cfzlvi5pkg","2nGWiEMKCQEr/EiJ9zyuTJSw6erRdV8HcL4MsZP2elQ=",-726883588247284503,-227143186727508837,891928808630154560,-3410499304065751323>()) {
                                    case -2028046713:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }
}
