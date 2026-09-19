package com.yiyiaddon.l.f;

import io.github.humbleui.skija.Canvas;

public abstract class c extends a {
   protected static final float jb = 14.0F;
   protected static final float jc = 6.0F;
   private final com.yiyiaddon.l.b.i f = new com.yiyiaddon.l.b.i(6.0F);
   private final com.yiyiaddon.l.b.i g = new com.yiyiaddon.l.b.i(6.0F);
   private com.yiyiaddon.l.b.g b;
   private float jd;
   private float je;
   private float jf;

   protected final void a(com.yiyiaddon.l.b.g var1) {
      this.b = var1;
   }

   protected final void b(com.yiyiaddon.l.b.g var1) {
      this.f.a(var1);
   }

   protected final void c(com.yiyiaddon.l.b.g var1) {
      this.g.a(var1);
   }

   @Override
   public float D() {
      float var1 = this.I();
      float var2 = this.J();
      float var3 = this.K();
      float var4 = var1;
      if (var2 > 0.0F) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s1dqals30llwl9","obQHcVhty25bvhS16AB7XkWRKvZC3RNGnmdFCGGX5WY=",-3540437542723424114,4327104397098231446,-4016375978161628420,328193678642913088>()) {
            case -914031146:
               float var10001;
               if (var1 > 0.0F) {
                  label46:
                  switch ((int)com.yiyiaddon.m.b.a<"s13le2wlnn7kbp","/Z2BJZxUP/ZUY5jZuaozTaKrz3tdfxVYLjULimj4W8s=",5798994743068143080,-3912081299532720750,-3433778935832270789,-4394536924151803141>()) {
                     case 1145950618:
                        var10001 = 14.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s16l470um0gl94","SHysoxP7zoujsfzDzA9oqY0f8VnnYE7r5WnT2eChZnQ=",-3837664475497755894,-3604418397154104799,-5639877230488293273,-604523256463635957>()) {
                           case 1568584449:
                              break label46;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s19c3leo3uhcgv","lQQ5pmnVSQbBM6P6+NY2KZQnnd82CoVzdhwCmjSNToQ=",7672089087572679125,3004910408929303093,6320388763927325509,-2545511115673261638>()) {
                     case 806551031:
                        break;
                     default:
                        throw null;
                  }
               }

               var4 += var10001 + var2;
               switch ((int)com.yiyiaddon.m.b.a<"s1keahcll9wn08","zYOec3CLba0zjvND/740apdfnz2kvOT+T9l6bR+sZ+M=",1305454241785065201,-7485781890700803604,-1238190218022265118,-712309446146438383>()) {
                  case 1887629387:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var3 > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1swysbt5vayme","km7d20FSVNi7NyCq0aIjOXgAJPEmUw2+i73IO80wKUI=",8369258420787161420,4485735309070052594,-4205979831677536181,2885717929842046568>()) {
            case -2070963065:
               float var5;
               if (var2 > 0.0F) {
                  label34:
                  switch ((int)com.yiyiaddon.m.b.a<"s7sxa64vdmlha","akgmvzRwMIqw1nCovKH/cPt2zH9RS/VRkKPVwYOtpI8=",5455141259169854528,-1352060243525096740,-1586738019369746434,-9128069376302304581>()) {
                     case 677448054:
                        var5 = 14.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"svytnez67toby","ERWj2dRYRO2UJOPQJLeUfuF+O4doyV3cQpX7SxaeUQY=",-4612608665304503960,-4911421364350594040,-4077991976471225326,1381188920702997072>()) {
                           case -1471576382:
                              break label34;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var5 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ji34e1lz3qqs","wAfI2ftwOWAV2aYyYBOB2MafpkUuFgEhbKkV+72eCUI=",652967978542448765,-8818752113696045421,6255126716026072768,8358434653516789022>()) {
                     case -1594180249:
                        break;
                     default:
                        throw null;
                  }
               }

               var4 += var5 + var3;
               switch ((int)com.yiyiaddon.m.b.a<"s1lotrjobwsu9c","vTJVIDg4XNcn2zqSYvKyKSo/j1qjJcZr308mwDETVLs=",-4312285482968191888,-785215805832101407,2572452885651281023,-4075320565602895258>()) {
                  case -968191148:
                     return var4;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var4;
      }
   }

   @Override
   public void a(float var1) {
      if (this.b != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s21368x1c2oo24","2WtKPhd/VxZehEb7r/+leWqhx6Px1HryxnyNZtiZUlM=",-4140825589485850157,1071892720103851488,1796597930396954511,-8409194931240621922>()) {
            case -2135084712:
               this.b.a(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1t6sa3pydezsn","UlH2/J9/SeqoU56VvWucLrhVXojP+fmQLX8xswJiweY=",2974833813208561763,5875140159523766581,-4309676649206159199,6072938746443240180>()) {
                  case 469423686:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.f.a(var1);
      this.g.a(var1);
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.g(var3 - var7);
      float var10 = var3;
      float var11 = var3 + var5;
      if (this.b != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37mlsiviat431","I7WbOnmFK5riSfCJzzun9ueCtES53s+AZIMWprCDf7w=",-6018172362102969625,-4911845156680363050,5285256022269261076,-411602115137820516>()) {
            case -876435992:
               if (this.jd + this.b.b() > var10) {
                  switch ((int)com.yiyiaddon.m.b.a<"suz4hxhbg7jy7","kg4HjceNFYUjY8ZPMlwsFAhprcElgwb9XDiAAH+Hs1Q=",915028662944657501,2725560816982658404,2360792717801817967,-417111105381917295>()) {
                     case -1201259566:
                        if (this.jd < var11) {
                           label19:
                           switch ((int)com.yiyiaddon.m.b.a<"s18566o24v128n","QT6DwV9Sjbs0KjBcWBqkJZsalT1poQsE/nIvNSWbPHM=",4432951630978211065,4147718410610024112,-1528309916873244213,-5768075724115584034>()) {
                              case 463759165:
                                 this.b.a(var1, var2, this.jd, var4, var6, var8, var9);
                                 switch ((int)com.yiyiaddon.m.b.a<"s8dghibhu37cj","84cCFo9aNJfrBNfXDOlsRJTxl/m986KrjemvbF1HZMM=",-3061718202274786351,-1532524441074731058,-1867765640056538331,4631818506864353853>()) {
                                    case -1987743727:
                                       break label19;
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

      this.f.a(var1, var2, this.je, var4, var6, var10, var11, var8, var9);
      this.g.a(var1, var2, this.jf, var4, var6, var10, var11, var8, var9);
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      this.g(var4 - var6);
      if (this.b != null) {
         switch ((int)com.yiyiaddon.m.b.a<"si1gr1xvxv412","3yaIxMr7SGCXKrMDbAvU7G/jhJOR6kRAThO1Q1PxKms=",-9158791158229407698,-5442149688391201670,8391306518109326957,7865693218910583763>()) {
            case -1105390854:
               if (this.b.a(var1, var2, var3, this.jd, var5, var7)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3u68nowlvnvcq","9qeB+W7CmMTHGHJC6IpFSVUo8Qj9+JNli8Ee9wY4Nhs=",6421475422795605061,-4295595861263586903,-6535610952715001576,479171407330823577>()) {
                     case 823534161:
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

      if (this.f.a(var1, var2, var3, this.je, var5, Float.MAX_VALUE, var7)) {
         switch ((int)com.yiyiaddon.m.b.a<"s33ge62z56uoql","M2GTWxvQfgVAFFT5S8HqP3C/b07xCIRkBUHPseoufik=",-8769088831817295450,-2460782934378061848,-4696396471123312156,-958780254914127033>()) {
            case -2073323936:
               return true;
            default:
               throw null;
         }
      } else {
         return this.g.a(var1, var2, var3, this.jf, var5, Float.MAX_VALUE, var7);
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.g(var4 - var6);
      if (this.f.a(var1, var2, var3, this.je, var5, Float.MAX_VALUE)) {
         switch ((int)com.yiyiaddon.m.b.a<"s7to9469tpu6","8DLEpkJkwbU5zXcp8VYW/5nW6mdo3vXNaJ0srDueYss=",8823147081797909184,-3065283310820936682,-3035946848854792332,7348537041396493618>()) {
            case -1942618800:
               return true;
            default:
               throw null;
         }
      } else {
         return this.g.a(var1, var2, var3, this.jf, var5, Float.MAX_VALUE);
      }
   }

   @Override
   public void F() {
      this.f.F();
      this.g.F();
   }

   private float I() {
      if (this.b == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tr97d5ershlu","+Arbim3yifqfEJ1N2gpUhO3nnfD3RdiGcsiykdEGhRc=",995430074697835900,4552364544141431799,-5116213792456588926,-6043283528350089898>()) {
            case -671230471:
               switch ((int)com.yiyiaddon.m.b.a<"s31sr7rg9o66tj","qPsn1qiE87w2TwT4k4LBEveX7Xzbb9PctomrOKyJHCo=",336388676385203493,8631356245080751712,-7117226086917189011,2534823269492838948>()) {
                  case -620035615:
                     return 0.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = this.b.b();
         switch ((int)com.yiyiaddon.m.b.a<"s1s31xrhyubv6g","LwnBg4d5XindIkV/1MfLx+IOPkRhfLpRUwocjOn9cQQ=",6418801689025066649,5324932517681493689,-1191142997619935819,-3407109219040018937>()) {
            case -1613849605:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private float J() {
      if (this.f.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pmfamot2331v","7S5PTF4o/XXjU++GvSqzFqg9l6Cv9+o5OlJVuNWdIGA=",8352822004976579517,-5857552679437302770,7998123010096872127,8933585427751846207>()) {
            case -2042491146:
               switch ((int)com.yiyiaddon.m.b.a<"s2nek7vif9xa81","IdvvBUyrXxucl3E+DrDjP1IKKRS14Mpb3oIu72WPerA=",1841930463578151322,-781600388796955213,-4171300977381219626,8715785775365161143>()) {
                  case -333042361:
                     return 0.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = this.f.b();
         switch ((int)com.yiyiaddon.m.b.a<"s2r3bg5r8p623n","fTH1fmBWszZL9djwZFWtCSwBiOqL8OC2wSqVWH2AjzI=",2879543295255077042,1480566561269083569,3325297141777412089,3690788428354025789>()) {
            case -2103492224:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private float K() {
      if (this.g.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"s37g0bmtin6ek8","/S74n6MqZF82CAGMz3tQBXMJviBQMGhsnxm9MFwCSqI=",-6431265006936897908,-5787411969648435235,-1766197917148438429,6842173220463345268>()) {
            case 778167144:
               switch ((int)com.yiyiaddon.m.b.a<"sequvdlytomod","s+cEa0sn3lYjP4kDllb7yCrXBddD8zaI51Kxs6heGdM=",-6561503365087952746,-8825306631401928562,2343557330710678540,-3104477221496023724>()) {
                  case -572810037:
                     return 0.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = this.g.b();
         switch ((int)com.yiyiaddon.m.b.a<"sdcjnhfjzi3nt","wiTZUvZFL96LWG3fRERIZmvv6UpYpyHa/A0UFVWkCVU=",-2041173414503061539,-9118576690528114526,-1090868945879705389,-3516989834647473960>()) {
            case 478243010:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private void g(float var1) {
      float var2 = this.I();
      float var3 = this.J();
      this.jd = var1;
      float var4 = var1 + var2;
      if (var3 > 0.0F) {
         label44:
         switch ((int)com.yiyiaddon.m.b.a<"s2093fjt0ur8yv","FMlaoUPPloIvT9TC/ckuaWo9f0z80sEIu9vsUYO7MUA=",6255949385140836700,-7373693128681507788,-2103349291313742932,9127948071653457980>()) {
            case -2071960203:
               float var10001;
               if (var2 > 0.0F) {
                  label39:
                  switch ((int)com.yiyiaddon.m.b.a<"s1o2sb0jb4twsb","xNbbKvNi4MC4ILMcxtkYDl4zIgIzyoYEetge4zUaTqQ=",4616341237636182492,-1522090456568417681,3299229121869132137,7456701154730427202>()) {
                     case -1974709540:
                        var10001 = 14.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2n7zj033rt6yj","To5dIjdYKmkHK5ZqjrP8Xo/Ufik/iwTOkH8bfwHxzsA=",-5239644281193081178,2333866235353172711,7946888241782003525,-185597300491654254>()) {
                           case 1679950673:
                              break label39;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s23h3l5u4g5pg4","io8LMgNBLuXmU3i2dJMJT659ne+z46HeBwwj0MxiM8I=",-8114585428969302714,-5413421082978594597,-363698463306935064,-7522188077737253417>()) {
                     case 1165239293:
                        break;
                     default:
                        throw null;
                  }
               }

               var4 += var10001;
               switch ((int)com.yiyiaddon.m.b.a<"s3vevugtysolli","MXHxXvOaYiimH1Y8nUfaT9mQUkO/OvYM9OKf90ViTfM=",-5168275771397197761,3222574653090813598,2670664037013326181,5400993350142807880>()) {
                  case 13748661:
                     break label44;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.je = var4;
      var4 += var3;
      if (this.g.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wgxjhlr4ktsh","wcnfQSFsfS71S1yql7rr/pFcAxLJ1pCAbq8DENcbia0=",-2689471640156556759,-1880050051751884137,3968180580585644415,3878169156598473990>()) {
            case 428784634:
               this.jf = var4;
               return;
            default:
               throw null;
         }
      } else {
         if (var3 > 0.0F) {
            label30:
            switch ((int)com.yiyiaddon.m.b.a<"s20c0h12355hee","zk30rsTJeyR9e7CjIjdveqCnA2/szT2LpnIqySgNxyU=",4850000614102103009,8324518085511559912,1307311756360953783,1432830052869472277>()) {
               case -452954171:
                  var4 += 14.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s2oueonts5j13m","0PCQg8l39aYupg+4X67bWBoC/Yd9KgG2FZKfwYgLmaY=",-1232518804492768708,-580258730427650935,-8663460752315951369,-5462383957820709972>()) {
                     case 1723053548:
                        break label30;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.jf = var4;
      }
   }
}
