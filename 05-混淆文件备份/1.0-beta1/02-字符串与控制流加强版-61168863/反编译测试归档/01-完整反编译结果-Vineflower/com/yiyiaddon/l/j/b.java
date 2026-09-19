package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.function.Supplier;

public class b extends p {
   public static final float nb = 24.0F;
   private static final float nc = 14.0F;
   private static final float nd = 0.34F;
   private final String GL;
   private final Runnable n;
   private final com.yiyiaddon.l.a.b g = new com.yiyiaddon.l.a.b();
   private final Paint B = new Paint().setAntiAlias(true);
   private float hP = 24.0F;
   private float ne = -1.0F;
   private boolean gC;
   private boolean gD = true;
   private boolean b;
   private Supplier<Boolean> E;
   private float cD = -1.0F;
   private boolean eh;
   private float nf;
   private float ng = -1.0F;

   public b(String var1, Runnable var2) {
      this.GL = var1 == null
         ? (String)com.yiyiaddon.m.b.a<"s2ttujzharv1fc","iQl9a0GFMysOfC06t0jRSLTgEgpU93dKiTfSfw==",3547234750190076154,-6390399113902700547,-8690723887993550021,-5474506831949417666>()
         : var1;
      this.n = var2 == null ? () -> {} : var2;
   }

   public b a(float var1) {
      if (var1 > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yj9gssvqong0","5PGplUso78CY5Ox5NHlkp02suPU7iL85eQ99SCEegz0=",823106946358370712,-1148957209154803604,6435948741219722125,1849175472607272011>()) {
            case -1442859879:
               this.hP = var1;
               switch ((int)com.yiyiaddon.m.b.a<"ss6vuztv3wpm8","nNf6EddoQ+BEwR/KXvnjo42ADt/+erRDDu1JG8T6sH8=",-3370796781692461561,2668120607501669202,4955298395627712972,-415937976947785235>()) {
                  case 253060337:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   public b b(float var1) {
      if (var1 > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"stj2tnrg6m1ka","OuZ8EiQAJQb1dVYgj+xmQA2XhQzRr3n+Z2XiAxtACik=",-2243220667557501086,8176201181106049002,2978832814916101457,-4519338074772028639>()) {
            case -216598575:
               this.ne = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s1caxpvec5okfg","oUWpLFIJBdsAZMHJMyHQ041QXbdnrUHl95G4N0B0NPg=",-469122885280338930,-277258724871944905,4071336045127860786,-4922294347945257870>()) {
                  case 1524930426:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   public b a() {
      this.gC = true;
      return this;
   }

   public b a(boolean var1) {
      boolean var10001;
      if (!var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"syherqeya0p2w","wmoCwP3OsnclsII7UuLmzcS9V1qhq0YGcIF6ZaKaOH4=",6359448839038465418,-341259100034223629,2124053252004235574,5096458259529092099>()) {
            case -1960167103:
               var10001 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s9fonc7jl3e64","wvfIsoUy0BcFGaVJ1DW0/+DS50bXI36kVv95gRJ9e6Q=",-153551297706864079,-1268818351910927770,-5601774210778057632,-4765341573686387593>()) {
                  case 1765923283:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s8tkh84p4lcn4","9CVAjevgbetxhJJuX2s/4jRhknfJ5zsENnPHIZcoLxI=",4460332208968574446,-6645284282699443828,-8156234567941847597,7856071895516106431>()) {
            case 282627826:
               break;
            default:
               throw null;
         }
      }

      this.gD = var10001;
      return this;
   }

   public b b(boolean var1) {
      this.b = var1;
      return this;
   }

   public b a(Supplier<Boolean> var1) {
      this.E = var1;
      return this;
   }

   @Override
   public float c() {
      return this.hP;
   }

   @Override
   public float d() {
      return this.hP;
   }

   private float au() {
      if (this.ne > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q9vy46ht5puv","NorEjHIK8/klJ5LwVGNrs8+B8YcI46C9jnzGRytsIv0=",6046469844102071247,1765270974533251107,5993782782507910307,8631838726895783603>()) {
            case -1834178385:
               float var10000 = this.ne;
               switch ((int)com.yiyiaddon.m.b.a<"ssuyeawee5j1f","GQDydnbvlUi2ZzUzkIWVgUlYn14RbIzrMvzrWoR/e9U=",-4898112821713827744,5685044771973489194,-7453705897844495976,-278305349264625343>()) {
                  case 585690359:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var1 = this.hP * 0.58F;
         switch ((int)com.yiyiaddon.m.b.a<"s3nj59napd52dq","9UrrpGg/vf3yLz8ZNCY1qoO5/b567B3rdHmY2MDkFq4=",-4267277280833218786,-2999847465400952073,-1120331175563744762,7281032936152121812>()) {
            case 802790753:
               return var1;
            default:
               throw null;
         }
      }
   }

   private float av() {
      float var1 = this.au();
      if (!(this.nf <= 0.0F)) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"s2wleflkilbis9","hT8WLOxx67c9BpezcFJ2rRJ0x9e/xVU8TsaFG0PKh5E=",-1604000194454973291,4311857508888086201,-9016489382429214592,-6133264899317121647>()) {
            case 656586429:
               if (this.ng == var1) {
                  return this.nf;
               }

               switch ((int)com.yiyiaddon.m.b.a<"sxt11ssaoj30g","zA6kMZ9xU3CMck+o1xo4A4arU/ZlZ1l2KGJdMwZq4ko=",9146163892500846917,7773974716441208583,3377444219014104817,2633989281511853010>()) {
                  case -929631030:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.ng = var1;
      this.nf = com.yiyiaddon.l.g.a.a(
         this.GL,
         var1,
         (String)com.yiyiaddon.m.b.a<"s1nzcvb356q2a0","pD7/+ZbdoJRadu74tIYJ7PNhptxPJTEU654RMeXKH8BQyBqEIbPM1tfZVcO6i7OCrMjdO9rQWwOW8kOM",682035465185882709,6458336379781022811,-7306754635930066196,-1170820948977901915>()
      );
      switch ((int)com.yiyiaddon.m.b.a<"sk056kcoea567","wxNlQeZ7zRX1uJatd2/kBvzQUSQc8Lbim9X++Rv3VB8=",-463347567678295846,1878226128085735198,1608338085712033005,7858837991573631200>()) {
         case -636988930:
            return this.nf;
         default:
            throw null;
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5) {
      if (this.b) {
         switch ((int)com.yiyiaddon.m.b.a<"sg5509tx4nrxw","Hxl0tznHXRycRUMKtEtFoBoDJH88fPHDiiTyO8vvm0g=",-8695717356568120977,949045983606802986,7399237780470635663,5344535603322131517>()) {
            case 282219621:
               this.eh = false;
               return;
            default:
               throw null;
         }
      } else {
         boolean var10001;
         label47: {
            if (var1 >= var3) {
               switch ((int)com.yiyiaddon.m.b.a<"seougmi2bacik","kiSanQAOhRpsO4X3STjpvbVkw74DuZIW/927Ha420Tc=",-2648212915732577217,-589453502940225653,5005271621592973127,-5194311575311751633>()) {
                  case -1000750633:
                     if (var1 <= var3 + this.hP) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2w5lpq02p9yga","TnaruKz2zylSz1pGcgnn+aImSpB5Rfh38l4vV1MYxaM=",-5848372514624185092,-4011028321975252188,-5682439475461635894,3012968188936903527>()) {
                           case -2007085529:
                              if (var2 >= var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1d2mpsrltuiwq","WO2g00b6gOozEs5OXiC7jdT0AyVavVh7LSe6tVPoP9U=",-127839995825776380,4378669542874032532,6097610789593092103,-5184939767898238916>()) {
                                    case -466805026:
                                       if (var2 <= var4 + this.hP) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s15o24z7q6juvn","EzzIEAC3jOQIxjsfPmmoHdzGec29lHROpmirZMGVefk=",-8924788645315684195,7684504167160896275,2985179487868906003,-2171486745558157761>()) {
                                             case -414698564:
                                                var10001 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s11gu58qekigyz","IlKK6iLL8wvHHNZFDSAKcSIEZrE5xPRFHFJ/RQHSVjw=",-462856505794442317,931161066150840993,-4841816169560304601,3764277287969328757>()) {
                                                   case 285691533:
                                                      break label47;
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
                     break;
                  default:
                     throw null;
               }
            }

            var10001 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s30ui2nk5yj552","eF6lmT1Q0dM+bdMNo/K/JvnbptcO6q0rHFHDel4k1zA=",3165489217708304592,1590013522548455697,-831836811015537130,-7216224344041564096>()) {
               case -1593921395:
                  break;
               default:
                  throw null;
            }
         }

         this.eh = var10001;
      }
   }

   @Override
   public void a(float var1) {
      if (this.E != null) {
         label37:
         switch ((int)com.yiyiaddon.m.b.a<"sj2l7c4ntv7gu","jQEZZho5M27/HoPkwbR8j0ML3u6WJ7ne8o1SfGKr/es=",4126440583814827142,-4708769649861048298,-8066512189126534652,-4006678936286116599>()) {
            case 1686595325:
               this.b = this.E.get();
               switch ((int)com.yiyiaddon.m.b.a<"s10gpqtrf6xewf","h+oXmbug9GLQ9cqL3Y6jA50xyzswkFWXxjoYsYFjZvk=",807476820820646687,-6516656237682828926,-6275603629448757582,-9063651953580172434>()) {
                  case -310700898:
                     break label37;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.g.a(var1);
      this.kW();
      float var10001 = this.cD;
      float var10002;
      if (this.eh) {
         label30:
         switch ((int)com.yiyiaddon.m.b.a<"s3q2h9h7pal62o","zcZfegWR/h5/ixZiwjPnIaDOH/ILqGPQasyO54GVbHU=",834340693883138660,-1786749029663801102,-2609061123931591633,-2864832891946760901>()) {
            case 378714335:
               var10002 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2z9mbxy6isjck","QNGdAnulxUgPfyrojDMMQJAscI2bZrNZFp/uS0r7T4c=",7375917915523260951,1240860705450007188,-8080185218425224524,-6960692212935203897>()) {
                  case 1977034792:
                     break label30;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s1i5aua9fdc6hg","8u3s/slMLisyI2wbLlj92TgWjzX6msREdZWjWQRwFTw=",-3005751273539264715,-1155804930592306208,9098138135429303735,-3559614243772469484>()) {
            case 275300178:
               break;
            default:
               throw null;
         }
      }

      this.cD = var10001 + (var10002 - this.cD) * (1.0F - (float)Math.exp(-Math.max(0.0F, var1) * 14.0F));
      if (this.cD < 0.001F) {
         switch ((int)com.yiyiaddon.m.b.a<"svx5fwwept1sw","6i7DmTeNN1oyIAnZkwx8HFWlPCa/LiwpFk4V7plW33A=",-2446172741894533995,-7361832880209364697,2613147304246725587,-767099482850823758>()) {
            case 1913966181:
               this.cD = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s3n81q52fehtjh","B5tjki6mAIqyWTAfCoDRykChX+1Hs7fYvg5r2gBX2HQ=",-2617916862499073670,-1722418390249571329,3273784910979391295,4766820805422075978>()) {
                  case 1350446083:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void kW() {
      if (this.cD < 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s7728qkf4a0he","HwQEYWMA+HMBZoHrt0xL1CLY90Ny0aoDjso5Qe+cuCc=",8792824280951520760,-4346069883366188961,-5569191890439329905,903616675700185062>()) {
            case -930090057:
               float var10001;
               if (this.eh) {
                  label24:
                  switch ((int)com.yiyiaddon.m.b.a<"s1ktp72ll0xqgj","RYnKzUe2fEjMZJQdIBY2OPCJRTPAVDp38KTX/kyRnaE=",-4590439342545828488,7621741035939122373,8916366660131788643,5400567013542472710>()) {
                     case -220100366:
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s14suuw7ux22i7","h1AL9zmhJPsNLviOTjOJ6b1qQKWm1XlO2BSMiq4sMmY=",-4207900525995833585,-531485720328616112,2388407474833997831,4762666153884524277>()) {
                           case -323645898:
                              break label24;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s33kn15s8kwjw5","ToYMGGzIAVO81NRnSauIWM2zqF1j/FkrOPB58kk3dxU=",-9190842223775094064,-6950736584443332040,-8190800973184756798,7272932673408350421>()) {
                     case 1171806532:
                        break;
                     default:
                        throw null;
                  }
               }

               this.cD = var10001;
               switch ((int)com.yiyiaddon.m.b.a<"slltmnli7tklu","+fv9tJDETRNVubD42vHE3Z9HYTR2N5TNC3j39R0IWUg=",-2869388920868968420,-4423965557044800061,949454222811547574,-1503362478233248524>()) {
                  case -1419925246:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean cB() {
      if (this.g.fP()) {
         label39:
         switch ((int)com.yiyiaddon.m.b.a<"s26pbpmxv61ff7","uxE6W8Al5u6Ig56RbZRS5D3JEEoWw5DxFj0Q6qQRzoY=",-3252461584350834490,-4219062546641892416,-4274897612947619968,-6898832702560350213>()) {
            case -2079425001:
               float var10000 = this.cD;
               float var10001;
               if (this.eh) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s2e2tqebws4aco","/BJ10YPlfXh4Qk8ho1qZOpF/M967Lw7OtB+XoGyhBig=",4497367801466760948,1433050730515659018,5239507144091198276,-7744995631392164403>()) {
                     case -1540166161:
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s34fqyry6wikb9","7lSqEVxxTWH9NIYkltNL+LTgz5dhT71mUG0IptmDMCk=",4225315630305494651,-8892779165875639508,7297212733382097386,-1290016236238756503>()) {
                           case -179822910:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s117q3x88g61eh","CCJd/i+BYdFlQHgA5fy41DiOyvHYZb5EdVMQe0LkVUw=",-6608233163517305232,-6484731858435029383,-8118804363611194673,-7166201882385153481>()) {
                     case -216250480:
                        break;
                     default:
                        throw null;
                  }
               }

               if (!(Math.abs(var10000 - var10001) > 0.01F)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ubm4hfl7jk9f","w2EIX5YlavUX78ingc74u00f3LitJfRM/RhXPdTD5N8=",4323215084272265047,-7486059455363696379,7641835497570577969,-374715310174761990>()) {
                     case 504795302:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1y9ejq5uvk4ha","L+xl6sDa6aCz1KgB0SW2JbV/duGOD55d4Jd1YAQjyqU=",-128948882894617090,-9078666287495919381,-5298849230242684713,-345293894683262254>()) {
                  case -920233320:
                     break label39;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3mhmiifn1eu6m","vE6ZY8bWQ4fOQ4l5hqntwzGFVTEkHOok38kFvMFD1rQ=",-5355222697356548755,-1181968718492745450,-3695826014366252611,7140290123296111728>()) {
         case 313664557:
            return true;
         default:
            throw null;
      }
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      this.kW();
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      float var10000;
      if (this.b) {
         label77:
         switch ((int)com.yiyiaddon.m.b.a<"s1bgdzyzhdgmpo","emouq5lGW1I/5Hqk3A4KfoUfkX2ZD3rn4zd5nn5ydVQ=",6443761023306781620,3385492343050031003,5694533698370014525,-6947350759776526312>()) {
            case 1996214934:
               var10000 = var4 * 0.4F;
               switch ((int)com.yiyiaddon.m.b.a<"scjnzyc5tuzdw","ZhFWDBIkxybvuPBGXaxhLanWDs9ZVBc52X4O98PvTu0=",4443844497051646066,110000044808885020,-8190575877316810958,-6786506419598358507>()) {
                  case -410783634:
                     break label77;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var4;
         switch ((int)com.yiyiaddon.m.b.a<"s1geey6yyo0whb","lX/q9q7Y95HiBPJSjsoZm36ILCEUf5ZDM/4XRUBnEPo=",1031433290622326939,762208647786463211,6632738545389392830,2899256684978135944>()) {
            case -61736203:
               break;
            default:
               throw null;
         }
      }

      float var6 = var10000;
      float var7 = this.hP * 0.34F;
      int var16;
      if (this.gC) {
         label70:
         switch ((int)com.yiyiaddon.m.b.a<"s2a5zivbagcxxt","VQlYdgxWzl341f0kiUU3WO0fntE4SBJaRJP1ZyXPirg=",-6517737118269177625,-4139282599148051646,-7141985297002254132,5308323494165067026>()) {
            case 1892016666:
               var16 = var5.vs;
               switch ((int)com.yiyiaddon.m.b.a<"s1zl5zofh68qij","d9f8fO4DPAYRkUomhxBptT+KmSSiBCx8NzvylfPl71c=",-1693154313432425243,2771324087809011173,-7334473614199375035,9093215592557389023>()) {
                  case -465012997:
                     break label70;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var16 = var5.vj;
         switch ((int)com.yiyiaddon.m.b.a<"srnsrquw23uhb","VZiHeq/d2FMuzAABtzdCGB5kxuyvsdWc6oR2DSYa+fU=",-634126735162331010,23274945326252325,-5605369502912903310,-8969512612116052214>()) {
            case 1773806609:
               break;
            default:
               throw null;
         }
      }

      int var8 = var16;
      float var9 = com.yiyiaddon.l.i.c.y(var6);
      float var10001;
      if (this.gD) {
         label63:
         switch ((int)com.yiyiaddon.m.b.a<"s39nfzadxkm1ju","w0myXud8fpdZ2gZoFtDWRburyk/QaIqp9Uw22YPPSfA=",-3127520505697017945,2975067150270256547,-417199427971360849,-4181253533925740479>()) {
            case -654958017:
               var10001 = this.cD;
               switch ((int)com.yiyiaddon.m.b.a<"s1d2dtrj3v9f3p","p1nN3r76m1EmXmCIqX6NbDoHuTGcQ1DPnyW0FTz1VmE=",1922127776477008861,-5154168887107998548,-5065063591336646385,-5355096924844157335>()) {
                  case 962398232:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = 0.6F + 0.4F * this.cD;
         switch ((int)com.yiyiaddon.m.b.a<"s2sonmusca5ugk","2Z5ZsHlXjK80fFQmaHucdsuIa9Br/N3JOHGJS7P1/Tg=",3185306285796835796,-42120880228879163,-6669883871348704349,4317153534481524434>()) {
            case -535498829:
               break;
            default:
               throw null;
         }
      }

      var9 *= var10001;
      int var17 = var5.uU;
      int var18;
      if (this.gC) {
         label56:
         switch ((int)com.yiyiaddon.m.b.a<"s3todzimsh9wpn","+STLXBVhMd5KTEp5QySVOkEXSFxydroiqIaMs8quyDI=",4991216032749254575,3100630018379827242,-4407969125332718618,345420097007442810>()) {
            case 1410913953:
               var18 = var5.vt;
               switch ((int)com.yiyiaddon.m.b.a<"slh8h711ysc7c","9mm6r82mA1UY5EJJv+64iA0+c36GcBKMfPMeTdUxEd4=",-1785079552649682587,6223363825562752443,1479754467387533319,2876210911872904108>()) {
                  case 884077499:
                     break label56;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var18 = var5.uT;
         switch ((int)com.yiyiaddon.m.b.a<"s21hgi0b9kq64f","XTArPc8So620RGe6HiKR5orjuvmawad4GmAADHCwfYE=",-6466395494335579510,-3420990019281632116,-564698451837869723,8921748930065994005>()) {
            case -815230043:
               break;
            default:
               throw null;
         }
      }

      int var10 = com.yiyiaddon.l.b.j.a(var17, var18, this.cD);
      boolean var11 = this.g.a(var1, var2, var3, this.hP, this.hP);
      if (var9 > 0.004F) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s3o76dxm1z1fz3","nEBt3bblFVUppXn7wQSfAKH/EQOxBGOy8GDBsvwR9UI=",9164328699394148857,2121014113516002516,1595436864451599777,-3883827359478932179>()) {
            case -19767685:
               this.B.setColor(com.yiyiaddon.l.b.j.a(var8, var9));
               var1.drawRRect(RRect.makeXYWH(var2, var3, this.hP, this.hP, var7), this.B);
               com.yiyiaddon.l.b.j.c(var1, var2, var3, this.hP, this.hP, var7, var5.uX, var9, 0.18F);
               switch ((int)com.yiyiaddon.m.b.a<"s8qn5zr9onne1","eHUcuKy8CcALHraqaSWx7leOl9kGcf/99SP/TUidxew=",770995477879542641,3008540144298545576,-1746926341392167611,6149920631861408783>()) {
                  case 967611675:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var12 = this.au();
      float var13 = this.av();
      float var14 = var3 + this.hP * 0.5F;
      com.yiyiaddon.l.g.a.a(
         var1,
         this.GL,
         var2 + (this.hP - var13) * 0.5F,
         com.yiyiaddon.l.b.d.c(var14, var12),
         var12,
         com.yiyiaddon.l.b.j.a(var10, var6),
         (String)com.yiyiaddon.m.b.a<"s1nzcvb356q2a0","pD7/+ZbdoJRadu74tIYJ7PNhptxPJTEU654RMeXKH8BQyBqEIbPM1tfZVcO6i7OCrMjdO9rQWwOW8kOM",682035465185882709,6458336379781022811,-7306754635930066196,-1170820948977901915>()
      );
      if (var11) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fes2v06v7p38","XrXCikBSqmYleNjmOJJQil/5i1xXHdcKXkEJHKZ9NaM=",-2103020625129477176,-1381830765568485116,5618602294800826525,-1790739966399102068>()) {
            case -1471061519:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s1l7xqwzx7o6rl","WnRmtnH1Ct9/0FWcsflpNj72Q5pfL7ZCG2E+0dpeWio=",147097427302718767,1108389670869920205,-1060513476486412660,-5795605087198258387>()) {
                  case -1000187488:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s203doe025cu6e","6Tsc27uUdvtQ5GSKrAw6KGAjfF1H6sCECcM/nDmuR/g=",4063068136676690260,-3186106176571908415,4436985262668958228,-6356823338845575961>()) {
            case -358576940:
               if (!this.b) {
                  if (!(var1 < var3)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s61tf8zga5y69","DFZkqcq3w7Durq1gEs4A7j1ktQ7T7RVjcVSRnBuZxvw=",7178725297920617416,-3198138966779542883,-8822800780210400574,333130875928168934>()) {
                        case -397709210:
                           if (!(var1 > var3 + this.hP)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3jfbf0io3zl99","eT8Xw/dqYMoeRPSrFY0wcAvi5EgZFJi3OT7EUe1DpSw=",1815829622370963554,-6999477631000679659,9159145943455104625,-5225710373699657908>()) {
                                 case 707515415:
                                    if (!(var2 < var4)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2x5aadzwk1iyq","vmWs1njoT3Zub5EaG00IYYHYlLsUn4RFLKczhpRkZzo=",5592579357099200639,3380981918590774455,-1877750595011332887,3755223622413598040>()) {
                                          case -2084991511:
                                             if (!(var2 > var4 + this.hP)) {
                                                this.g.jL();
                                                this.n.run();
                                                return true;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"stmijcvsb604f","Rxk9CjiNH4MwVHyDk96wuqAupBbtbZyAnueznPyH18I=",-3055166709323781257,5347746504486159487,4222884217567348751,4817239443823009033>()) {
                                                case 1395952453:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sgrz8b2o8tcfu","213rlwb/KbIR/bs+WB29aNXKRhJQ/zlDFA0GBFrk0Ts=",4316726868747732566,-9065252388378573407,-1812637992812381746,7291690616800307232>()) {
                     case 233793137:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }
}
