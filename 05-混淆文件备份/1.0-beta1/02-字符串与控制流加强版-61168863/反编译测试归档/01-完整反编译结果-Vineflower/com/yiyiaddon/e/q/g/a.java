package com.yiyiaddon.e.q.g;

import com.yiyiaddon.m.b;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public final class a {
   private final Minecraft ar;
   private BlockPos U = null;
   private BlockPos t = BlockPos.ZERO;
   private int bi = 0;
   private int iY = 0;
   private static final int rb = 3600;
   private static final int rc = 5;

   public a() {
      this.ar = Minecraft.getInstance();
   }

   public boolean D(BlockPos var1) {
      BlockPos var2 = this.f(var1);
      if (var2 == null) {
         switch ((int)b.a<"s2lggycnp35upo","khyC7O8MeQsT8Oc2nJsXHqVlCOybFJz7PsqE7EYujBs=",4056209981710734180,6775136710105997393,7306560223527857221,-1113519175572235347>()) {
            case -92732154:
               var2 = var1;
               switch ((int)b.a<"splakny7t85bz","EJnWXxvwn/DB+QUEFUAVD8WtACp0/6I9R6N/OCiTeJo=",-8043165808344325874,5340640554932928450,-7176520455602163162,5043049890488797773>()) {
                  case -754204114:
                     return this.c(var2, 0);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this.c(var2, 0);
      }
   }

   public boolean d(BlockPos var1, BlockPos var2) {
      if (var2 != null) {
         switch ((int)b.a<"s2ngnovz3f6qw7","HREdgxDf+eEztkcD/Wu/r1PoGjdX4DBWXtoiZ5C3FIQ=",3926887417892538130,-7286948459468128558,-6697978109423707495,2730332217304418295>()) {
            case 758657359:
               BlockPos var3 = this.a(var1, var2);
               if (var3 != null) {
                  switch ((int)b.a<"s1fbi4e9myash9","yrnPXMaQpm2pQvUShWPeRJHNQWJ3zOsZpbnpCT5jJfk=",4097935259718856712,4977928314484890338,5698323417318840273,4212336015552424359>()) {
                     case 2003667956:
                        this.U = var3;
                        return this.c(var3, 0);
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.U = null;
      return this.c(var1, 2);
   }

   public BlockPos C() {
      return this.U;
   }

   private BlockPos a(BlockPos var1, BlockPos var2) {
      if (this.ar.level == null) {
         switch ((int)b.a<"s2w2zu799oltqi","b0PTz7rg4LmyJprg2qzWXB71ydXK1ksJ6mwOI7u6iwM=",82179194001835249,818208196816921340,-8586440740076201852,7584041459491359384>()) {
            case 826396375:
               return null;
            default:
               throw null;
         }
      } else {
         int var3 = var2.getX() - var1.getX();
         int var4 = var2.getZ() - var1.getZ();
         if (var3 == 0) {
            switch ((int)b.a<"se4mniz1ax1ss","C8BeTXiolxLkiRxZNlQ/xQFQNtmqmqW7Ps0bOP4rUgw=",6977951718636941789,3378160308896292837,1923131410018613593,-903925542460182367>()) {
               case -573731982:
                  if (var4 == 0) {
                     switch ((int)b.a<"s32h8jw0y6pifu","YzGuKFkrhpeanbfyMHMxWnUHhYMNUq9wPmXNUnFyVrE=",6539591264896048895,-75007613896326930,946751686335182160,-8587881272439634853>()) {
                        case 2140561141:
                           return null;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         Direction var5;
         if (Math.abs(var3) >= Math.abs(var4)) {
            label63:
            switch ((int)b.a<"s3lvaefmh2bvpy","3QGC/elJQ1FitMtrH5zgJXIhkTpBONbnbVjelSUwjF0=",932607673424308381,8615421923337372980,8067688569023127333,-976419619323831833>()) {
               case -1354902542:
                  Direction var10000;
                  if (var3 > 0) {
                     label58:
                     switch ((int)b.a<"s2hrdgn3lbdfm0","hSTwkWIdxzYRi/GLzNXm76tYQq/ByfAJVLk/KDPwMHQ=",2335631745117137899,-580510219839037298,-4518705346993930265,7225926646122993649>()) {
                        case -2008518993:
                           var10000 = Direction.EAST;
                           switch ((int)b.a<"s1kizy0l6eo2xv","lJtiSAqDF10j+9MkevIvxL8pC1LnnxES2C08UWyI4lU=",-7435288680671738548,1731812113378816380,-4098265507910718027,1340836663834615858>()) {
                              case -1857671138:
                                 break label58;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = Direction.WEST;
                     switch ((int)b.a<"ssa3v2nqijcuf","dGPWgnAAA65by8qKn/mMxFCsNEZ5BMr7ndTWy4IA9Tc=",621800465596076239,3970648415627081494,647322898399593095,8617170353038579561>()) {
                        case -2012414090:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var5 = var10000;
                  switch ((int)b.a<"s3nnsim4cr8ebq","t5a0/4LxVpgOOGFMAUmJJIpRbNvQgedSRbqwOOP4O+0=",-8803641418963918642,-7452830177209014211,-5765164554535930549,-5661136862618813321>()) {
                     case -1937942766:
                        break label63;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            Direction var7;
            if (var4 > 0) {
               label68:
               switch ((int)b.a<"s3vdcgbu8833n8","2Ps2y3lR1nEMjrMKmMalb4pm2Yr2HxGyOfgPy/VGyfc=",8044849919247371235,-3080058682978674892,-6853217064470538917,-1494720611263975716>()) {
                  case -2015614254:
                     var7 = Direction.SOUTH;
                     switch ((int)b.a<"s2my55n426zvdp","cdrOLs8v6Oix30onnSeVrWZ3rC+pMlpzRVyaAlG9+oQ=",-4961276343983875805,-9108083549902618557,-5644425577124467224,-4040043661779648952>()) {
                        case 6383789:
                           break label68;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var7 = Direction.NORTH;
               switch ((int)b.a<"s32zvxm0tdbuyt","u1Ow/I79fRvHBHNqINp+28I/ev7lnsk7obJyhi9fiLA=",7846010314001934953,-8672195637850291482,6218302052076213396,-7442453042459117841>()) {
                  case -2097532214:
                     break;
                  default:
                     throw null;
               }
            }

            var5 = var7;
            switch ((int)b.a<"s2tobhtyv5xx69","lGcNQkw+2QGotIVu3Kw9gATG/RyLNJ3FeG3z1so/Sa8=",-6524331333913748002,-3275528231557771150,-4338721841218937956,7758265795773449655>()) {
               case 2041905973:
                  break;
               default:
                  throw null;
            }
         }

         BlockPos var6 = var2.relative(var5);
         if (this.i(var6)) {
            switch ((int)b.a<"sk7ezz1uopmmw","ajTZEWgF+bkzoc3u6n6Npq4zEiFLTT/oM2UZHUKYoKs=",4872583981462456841,134037879236705113,4841422861224946070,681193346855439644>()) {
               case 1333742455:
                  switch ((int)b.a<"s2tgsovcogsalt","uXo+h/SVuBuBIryh2+Ji9dYqlK38U43tm2e1chAm/EA=",5175434898082526921,-7834136429169368735,5425462477065990821,-7838444819733176729>()) {
                     case 1687259009:
                        return var6;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)b.a<"sm3lqhpri6vhw","CWDhMmyNvXljBSHCyc2zOxahFa9DbV67c6OPNJUswYo=",1928437520685943386,-6235800760497651673,4082310373695080313,-6329605157088642201>()) {
               case -561119139:
                  return null;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean c(BlockPos var1, int var2) {
      if (this.ar.player == null) {
         switch ((int)b.a<"s3tszzty5xjbrg","zC6y5Zh9YHOPQRhCoYrz2xVY3P/XxnG7ZpJiqFKHecQ=",1484527715674226752,4844151247733237907,757733002082689429,7341992619294646383>()) {
            case 1400929365:
               return false;
            default:
               throw null;
         }
      } else if (!com.yiyiaddon.i.c.a.b(var1, var2)) {
         switch ((int)b.a<"s4gij154nbybs","OT5gH8oGsuNO7jnhec2saJRjwzWmdCR7DJQhYlJrV00=",-2537873977547898670,-6291081557878327082,-2380709452319571631,9090276005207854391>()) {
            case 1200374588:
               return false;
            default:
               throw null;
         }
      } else {
         this.t = this.ar.player.blockPosition();
         this.bi = 0;
         this.iY = 0;
         return true;
      }
   }

   private BlockPos f(BlockPos var1) {
      if (this.ar.level == null) {
         switch ((int)b.a<"s1w33md89vxn7h","vf8EVRws41uS04ikDNnTNjgtwzRY10N+8AHFyX39GiA=",-8337419883278573522,-3292943315752307656,5996115361377155113,2766747367319443783>()) {
            case -2131655569:
               return null;
            default:
               throw null;
         }
      } else {
         Direction[] var2 = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
         Direction[] var3 = var2;
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)b.a<"sn55fmkjw4kyy","fKmtbYxmXdLm6cLnV8n4xKPOLQcTBctm627xrPMvLE4=",-7020974413716490202,2021922495050295642,1749958739427013207,1058799079762777331>()) {
            case -1969095965:
               while (var5 < var4) {
                  switch ((int)b.a<"s32hjmokthf9mw","ZUfJWGMkBHZ4y2LfHUVtRXWvkelHH8kZ1hFT30+Zdbg=",974836450388240124,2207018059700061959,-5509302248400923040,-6088354553589352299>()) {
                     case 168688079:
                        Direction var6 = var3[var5];
                        BlockPos var7 = var1.relative(var6);
                        if (this.i(var7)) {
                           switch ((int)b.a<"s5sz4uhgfo9jf","K6GhvyuGfkjsgxuerXsXJk/vee+kmXTUOYNw8qP2F9o=",-2070496330844988458,8651095150759539413,2106015956925288893,3293847392431608132>()) {
                              case -363866143:
                                 return var7;
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)b.a<"s1xdyjjvydqnjh","3UvidZooHduMeph143dpqsrYh17fCWy6fuCLND4K5WY=",-1062628557702725481,-2009166424908334310,-597579398989088754,-7579709377579402754>()) {
                           case 376351282:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return null;
            default:
               throw null;
         }
      }
   }

   private boolean i(BlockPos var1) {
      if (this.ar.level == null) {
         switch ((int)b.a<"s1dhwf4edjqohm","thKnvHVr0ONW5JlM0LCn4lMgXhPqnhWwFqF61H1QgjE=",4398553057734086483,-7927598142228518919,-7180453105755496763,-1666337037084969886>()) {
            case 1758988838:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var2 = var1.below();
         BlockState var3 = this.ar.level.getBlockState(var2);
         if (!var3.isCollisionShapeFullBlock(this.ar.level, var2)) {
            switch ((int)b.a<"sohv2uab98zu3","od6BulnVlDPXaP+vNrBvX71N+aELvOUscFl3N3dMmXs=",4256556858459350074,8484274534534735636,-1725797028491302183,3067077377658386935>()) {
               case 892523402:
                  return false;
               default:
                  throw null;
            }
         } else {
            BlockState var4 = this.ar.level.getBlockState(var1);
            BlockState var5 = this.ar.level.getBlockState(var1.above());
            if (!var4.isCollisionShapeFullBlock(this.ar.level, var1)) {
               switch ((int)b.a<"s2jpmrsqe6fmiq","ZURrBz2/V3iP8pfMGZYzNjPCn3mlc7/bhGRIMaXnL90=",-3889589123554393971,-3466079342404338911,7116553411854113506,4485569034686464615>()) {
                  case 925619362:
                     if (!var5.isCollisionShapeFullBlock(this.ar.level, var1.above())) {
                        switch ((int)b.a<"s36db9od201lxr","aiBcO/N9k9bYtyU1Bv8LYOayJaTtKDgbLu2BhpQPs7o=",6884142112273345772,7402966328593528878,6416515926984158364,6480245853508403862>()) {
                           case 426042146:
                              switch ((int)b.a<"s1t56lz4pe0q1s","Pw6QfoHDmhHP0C/pHjKKA2d1k/32w7+4dnHMOcnVvDc=",2121791457480733812,5792335937527111464,262650944119953775,-6098069423365091608>()) {
                                 case -601508062:
                                    return true;
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

            switch ((int)b.a<"s3n9lzgjd14cka","ejEib7oHiDTFUqVcYeewpwEVzGsq4BaaxigwJYF7EuA=",718838439826167400,-673448880947170509,-2765410115579259466,-1838266225701508272>()) {
               case 175166640:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean b(BlockPos var1, double var2) {
      if (this.ar.player == null) {
         switch ((int)b.a<"s2794jwbqai0tt","PWOIoX+faaL8qUYAmJ8P3cmPr5caFSONwFgJ6Q5q0W0=",257598345728079574,-6022326842111383266,-4232401287166190367,5744453728918696815>()) {
            case 1308314389:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var4 = this.ar.player.blockPosition();
         if (var4.distSqr(var1) <= var2 * var2) {
            switch ((int)b.a<"s3ppqc2vdhxq0p","Wyw5UZmwGvoJLxuWF0icTsE4v1+53IheDWqoxtVXfkI=",-6488354320597047930,-2587085019866759348,-3938581020038595058,-419221397852440832>()) {
               case -900944100:
                  switch ((int)b.a<"s1gqrr3aoex506","3bUBiEoKKRq5mRhWC0PKq8uNG01OHGBoGwn0byHQijM=",-249705672086491816,7164672222241797944,-3461540281252396179,2016146880000784419>()) {
                     case -528313363:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)b.a<"s9zzujuk3x0lh","m7sYDhM/fSDNlSJ2ljd7qnYKMaY+kjR8pdTQhDA8vjA=",9093315565838747710,1940478491310701771,4710324029062267989,-2766275934339807127>()) {
               case 70006266:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean w() {
      return com.yiyiaddon.i.c.a.cX();
   }

   public void ag() {
      com.yiyiaddon.i.c.a.i();
      this.iW();
      this.U = null;
   }

   public boolean cn() {
      if (this.ar.player == null) {
         switch ((int)b.a<"s3qcc0tco2ppvq","bPQ2Aukbhc7OLX+L2qFwo+oxUBsBQAZX/5Ai3O3NI7I=",-1803018727009742267,1769916121415543002,-8175059594226117732,-7238450724895774921>()) {
            case -245812320:
               return false;
            default:
               throw null;
         }
      } else if (!com.yiyiaddon.i.c.a.cX()) {
         switch ((int)b.a<"sm02x3afn27lt","/H5qPi5BrBShss904uhjPAUeKRVlBpzgj1dFaAYjoxE=",-572894056208694746,-7024197024524715226,3704434305362516903,-1699444544129217907>()) {
            case -1558164885:
               return false;
            default:
               throw null;
         }
      } else {
         this.bi++;
         if (this.bi - this.iY < 3600) {
            switch ((int)b.a<"s148w7ifg6ak4j","jblYAUmYAUiIiSxP7KRYAirOH5xRbr85cieQnA6pYLo=",-8114544256174752865,134622884527775119,9151141891769056808,-2411047293734640047>()) {
               case 890837990:
                  return false;
               default:
                  throw null;
            }
         } else {
            BlockPos var1 = this.ar.player.blockPosition();
            double var2 = Math.sqrt(var1.distSqr(this.t));
            this.iY = this.bi;
            this.t = var1;
            if (var2 < 5.0) {
               switch ((int)b.a<"sb238rpkl04kb","AS5uvdgXUNOcsVphxrU6mCpCHjNjJBzCmXCHBFijRmc=",7047350592380173417,1282844982528619361,-3525801428275945758,-7822349859360732957>()) {
                  case -764373736:
                     switch ((int)b.a<"s8aebz3o2st2q","TOEheJJGwkFMyVuLK/bbFAFez7Yv1cPN56iSs4Sziu0=",6923347851661036811,-4845056848472252130,-2149559047385868458,-416159807331229698>()) {
                        case 892319341:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)b.a<"s2s4zaoylb7w47","oCRPbbF8rVGwZdhHS6IOFuXHpr4TNDrFi9k37V39olU=",-6030300544757045176,20957999542327349,-4017884351609644768,-6716730294613047667>()) {
                  case 1271155093:
                     return false;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public void iW() {
      if (this.ar.player != null) {
         label13:
         switch ((int)b.a<"s1j0ic0dvf4juf","RqluQwY4cGXG/Fb2p3nLH0Liazx/5izAysji42icgB8=",5343551000067779144,1065501563529224711,-2662984591425222448,-4894095141923471118>()) {
            case -1255543004:
               this.t = this.ar.player.blockPosition();
               switch ((int)b.a<"s3gtuzjgbbfey","ZmRt7W/vEEjjHxl7a1FRtmVVJADiT8kaDrkW6jRaInk=",-7890885701706318088,135215012737985993,6919441138222848106,-3583404068330429429>()) {
                  case 801897249:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.bi = 0;
      this.iY = 0;
   }

   public boolean bo() {
      return com.yiyiaddon.i.c.a.ft();
   }
}
