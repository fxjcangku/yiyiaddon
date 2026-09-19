package com.yiyiaddon.e.e;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.o.b.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public final class a extends com.yiyiaddon.d.b.a implements c.a {
   public static final String fD = "bonemeal";
   public static final String fE = "自动骨粉";
   private static final int cU = 3;
   private static final String fF = (String)com.yiyiaddon.m.b.a<"sd7srdwlg4pvj","uL4BeLzsW5sS1yELQEwFFCeit1ykEua9BZc/FniVQ6tuzeL4",-9158866120191466065,-8959697197114090302,-6527247848747810599,-8433992199041053728>();
   private static final String fG = (String)com.yiyiaddon.m.b.a<"s10dvqc4xpgwas","r8X9k970O074cq3eyKrsyEUOlPXGf0PR1nScsILWGzg1DfGQlamm4w==",-4385301921703996331,4832362919425191921,-4795162800455118649,-5943141506491706961>();
   private final Minecraft s = Minecraft.getInstance();
   private final com.yiyiaddon.e.e.a.a a = new com.yiyiaddon.e.e.a.a();
   private final com.yiyiaddon.e.e.d.b a = new com.yiyiaddon.e.e.d.b();
   private final com.yiyiaddon.e.e.d.a a = new com.yiyiaddon.e.e.d.a();
   private final com.yiyiaddon.e.e.c.a a = new com.yiyiaddon.e.e.c.a(this);
   private int cV;
   private final List<BlockPos> G = new ArrayList<>();
   private final Deque<BlockPos> b = new ArrayDeque<>();
   private boolean aB;
   private boolean aC = true;
   private static final String fH = (String)com.yiyiaddon.m.b.a<"senctw5wgyjp7","1ygzHWVrhNczUC3s959SWvuS1XRpNduvltFppIxb",-7214716402428043292,-9078519552777805214,2112112242169684880,-2599512356536315064>();

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s2r7xtqa973zcj","B4GNZK4DUaZhCnMlFTZNAt9xh2n1TrA/ssAf9nPSAEysCvdOgMjU4rKZ01I=",-1592675255271499229,-8298580339373495502,-7477127484779655404,-1818061152106267076>(),
         (String)com.yiyiaddon.m.b.a<"s3v0oku6xelhry","yWbH5jrw6jeoEuiPJ1yVdTFfMUcAtsJpO3xrVNXH+OmvAsm/",-4875631957832096979,5231489072311723443,-3156582459417019681,3496409565885202076>(),
         (String)com.yiyiaddon.m.b.a<"s2p0fmkxtz4jvb","TNP7GmDkNQGsKhhZnqdsK/0RlVWNKVp7+2lIhT9ZcuKVVtKmsoERZfo1JPlWLvo5",2767839100562838761,-3355859061930896252,7686369814550033642,5388822530707628671>(),
         (String)com.yiyiaddon.m.b.a<"s2pyyve2flbokd","DtJ+zpcTlHR/Tt6ZLqEBvNSGzaJtaNcHC/qMo/VO/kIKpg0TgZgiqFle3Wj28TZPN4O3Da6DGBetanYymR4Hn4EM6Qj8BvJxHPd0AlJc8HU8LKs9/uqtTwAEYJfsPr6wyl4jFA==",5243450690822454595,-4778215385863565891,-2938475836359787189,1721826229356611336>()
      );
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"senctw5wgyjp7","1ygzHWVrhNczUC3s959SWvuS1XRpNduvltFppIxb",-7214716402428043292,-9078519552777805214,2112112242169684880,-2599512356536315064>();
   }

   @Override
   public int i() {
      return 30;
   }

   public com.yiyiaddon.e.e.a.a a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      e.d(this);
   }

   public List<BlockPos> v() {
      return this.G;
   }

   public int G() {
      return this.G.size();
   }

   public int H() {
      return this.b.size();
   }

   public boolean av() {
      return this.aB;
   }

   public boolean aw() {
      if (this.s.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gu27bm2moi63","o/SXXOSamt76eM9ss02kTUoVNKStIX4e/t7l11YqU4Y=",5272675807684928314,-6629780901351126468,-3525828889225857614,3269272319976494294>()) {
            case 261097278:
               return false;
            default:
               throw null;
         }
      } else {
         if (!this.s.player.getOffhandItem().is(Items.BONE_MEAL)) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s1v8hprsq15vr","s1sici/0t9T8057AINB1FkoI3tSMDPpYnbIjuZevY20=",8869829106856404648,1497011837008473830,7621554689517500084,-8343067766734346659>()) {
               case 2101279638:
                  if (!this.s.player.getMainHandItem().is(Items.BONE_MEAL)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sy1ki1tpkbmwv","OLrW/5b+C8kxSTIThfmYDi6uW/I6zHqH7bXgHwMezhw=",4230766852741977087,-1535102896586796835,7975070944214589217,-296000519642550927>()) {
                        case -1984301369:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1trq95s1ad8o2","OQAj504eykMuhFkiMD/PaET8j3eAsVRoBZrscHn2yDE=",5247306227096997212,-5418647323483355373,-8984539964172696651,-3486808828898831783>()) {
                     case -857264487:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s3f4vhaa0o94te","8wyV43lg7dSIr2Y/rWLNR8s3I8f1BkJhufBaKpQg7oM=",-808895469183921078,5290426077656890023,7109012546009673441,-2421839376667048022>()) {
            case -1402460854:
               return true;
            default:
               throw null;
         }
      }
   }

   public String am() {
      if (this.s.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gq90lyz2hdpv","YGRFUSUckqmgi8WhCSGrOpxWdKsujzZy6DpMSE51w88=",4973573861777538669,5290844191502685017,-1088551262620126982,985084408315595552>()) {
            case 426925974:
               return null;
            default:
               throw null;
         }
      } else if (!this.aw()) {
         switch ((int)com.yiyiaddon.m.b.a<"s11khi7evhrtjb","3oaSzWg9ftDgriL+SLlhkbZTmojwWjeH9hP6A/UlaOw=",-7462761661827792549,2991260690357315016,1130778971993238240,-6226573438265719344>()) {
            case -867072773:
               return (String)com.yiyiaddon.m.b.a<"s2lji1nd4bhgpa","OwRDV4L9vO4ArshIZjWufHwEc5PtkVRuSg3MFsSf6pRnAQvx/4U=",5980750972700469201,3968296712685985558,-3019218858214549394,-313954002972594918>();
            default:
               throw null;
         }
      } else if (!this.G.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sxhvts7tqa95c","CC99lnIT/xc+GA9DcygdWv/L62hWF+anIA1lLzqzkl4=",-8572948643879086938,6417301832109146402,2294416083502655442,-3467732045821773257>()) {
            case -371832621:
               return this.G.size() + "";
            default:
               throw null;
         }
      } else if (this.a.a == com.yiyiaddon.e.e.b.a.准星精准指向) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k0sl7r9d5khe","zAXGuvZgYCNXh/KwCCPyM5xdmY3CTh1931BG3ZrvyaE=",6830423132695205372,6143771280001128047,-3336474439846869383,9073491018112291024>()) {
            case -1474902380:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1eks4b5iquujg","drXp3QybJ/WBSKdvGBpWfuemhCUtT8Lxgc6AvWcWh/vvOo+8FvO9Lw==",-1363435274372864809,-8277273414420278453,-681722302906461261,5503019309793746832>();
               switch ((int)com.yiyiaddon.m.b.a<"s3h4lyxyerizpk","i6eZiksreh4ghfMH1cL61/DSYG+IZVYihK1apWa30es=",7287006335600687823,8160017650735170561,4058908110504436766,4945233355042309857>()) {
                  case 2034695306:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = (String)com.yiyiaddon.m.b.a<"s1m4rlqrg5x3q1","/EC/uu/qEhb5qFa8/bO1tJ2DHgeMOv6EjsTJU4OTxj47Mwbnj14=",-1349322022858050862,5319426566995448204,7650764799078600357,7738873266877203776>();
         switch ((int)com.yiyiaddon.m.b.a<"s187lijz4jto58","4ICg+hg4/BPbDs7Wi5Ff5AXWBjnnAMZ8KCTD307CERM=",5019442899252421393,-4241913579954421163,-8510245694889138453,-8829830027272752929>()) {
            case -917510443:
               return var1;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void m() {
      if (this.s.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2i9dyv6dbq214","trayzaHyv40DOnAirEuj4J8NJJSMGwokZuZ0Jf24Nvg=",-386119922557594922,-6716244011562096886,5899639538839640114,1630345942233051811>()) {
            case 837039810:
               if (this.s.level != null) {
                  label20:
                  switch ((int)com.yiyiaddon.m.b.a<"srdqgwpw9omv9","zCzMMopLcl3UsSw3PBLixO/5wr+BOLq+j+u1RHjQ278=",-3325427155171726216,1576034425251800438,1714643325119565571,1430447801830515531>()) {
                     case -1267255866:
                        if (this.s.gameMode != null) {
                           this.cV = 0;
                           this.G.clear();
                           this.b.clear();
                           this.a.bo();
                           this.aB = false;
                           this.aC = true;
                           c.a(this);
                           l.a(
                              (String)com.yiyiaddon.m.b.a<"s2r7xtqa973zcj","B4GNZK4DUaZhCnMlFTZNAt9xh2n1TrA/ssAf9nPSAEysCvdOgMjU4rKZ01I=",-1592675255271499229,-8298580339373495502,-7477127484779655404,-1818061152106267076>(),
                              this.a::render
                           );
                           this.ao();
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1dz0ssq1scby0","IY1svv2LI+QAzLyGisLXouCKdxwMgwuacGMFjTMWPFo=",3546355485505070158,44696783186542752,1528459018444926097,-7896923338250042281>()) {
                           case 1745616078:
                              break label20;
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

      this.o(
         (String)com.yiyiaddon.m.b.a<"s2y1awa1ms8bld","ZRfiRw5GStTAcIv7/ajdBs1urFbnbVtEfPQcRZdVHPQ/LJLnzIO6HdODZByOWUJu45ZsPXyn5hbZNg==",-7240233315809116444,-1091784939372570100,-6227937829300963102,-4616962315933356949>()
      );
      this.s
         .execute(
            () -> e.a(
               (String)com.yiyiaddon.m.b.a<"s2r7xtqa973zcj","B4GNZK4DUaZhCnMlFTZNAt9xh2n1TrA/ssAf9nPSAEysCvdOgMjU4rKZ01I=",-1592675255271499229,-8298580339373495502,-7477127484779655404,-1818061152106267076>(),
               false
            )
         );
   }

   @Override
   protected void n() {
      this.G.clear();
      this.aB = false;
      c.b(this);
      l.l(
         (String)com.yiyiaddon.m.b.a<"s2r7xtqa973zcj","B4GNZK4DUaZhCnMlFTZNAt9xh2n1TrA/ssAf9nPSAEysCvdOgMjU4rKZ01I=",-1592675255271499229,-8298580339373495502,-7477127484779655404,-1818061152106267076>()
      );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
   }

   @Override
   public List<String> f() {
      if (this.s.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sn3ntdjvz4xcj","0Qc8SWC+Wv25JwvJEcUW1LivFdtdYWP5KUMlB2kwIxk=",8200526465499768522,6389353823056665239,-8360832483003625553,4196028947770252158>()) {
            case -1807101677:
               if (this.s.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dy3n3nqy8w01","fjAoTPwY+6tcWRgHSgClBz2LZEREuRdJx0oAyilW1uA=",-7479492601354894850,3854271686108579159,-91553474533776371,-5585443760373790888>()) {
                     case 1680517492:
                        if (this.s.gameMode != null) {
                           ArrayList var1 = new ArrayList();
                           if (this.a.ax()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2bdzu9jdn628e","bVockSkgeMVXDH7HO0pTEpXcRIS5DCF07rVA9kK/7kE=",-7714153930664501862,2098399813376658085,-677620429351038697,4087351860280059768>()) {
                                 case -1262950714:
                                    var1.add(
                                       (String)com.yiyiaddon.m.b.a<"s2kpti52wkqu2e","lQG7O35X/xUjbopGVgvkIWYVlymM1XMnP0UlPwb7LHJ2t6o6/A+CMVsEgliRUVPOILB2ed7LZpO4Ra+icCvgG0PH",3352189723578894590,1690934395970656976,-1943557826868410208,1615175525445049698>()
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"smjq3odz9ccv6","a5NS3vvnKECymnvU+6aR5sAMwFbstmnG/1C4FhD5HHQ=",1981844215960710440,-4686173259833471816,1201433827846301644,8873649498480320774>()) {
                                       case -1188255104:
                                          return var1;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return var1;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sulutsehrhw0z","FMxlFct/FMrejvLRv3GdGtAt+3tULEBrBmKp9bjtDaw=",8405804714573483971,-4835471369287511121,2100439079915725171,1664706284294217616>()) {
                           case 918994887:
                              return List.of();
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

      return List.of();
   }

   @Override
   public void F(String var1) {
      if (this.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dnzuc63r9ym6","uPMSGRbbXLfYugHKQU1JnGky7CjaD7g/mBuuFoZDciA=",-1572524262803156375,-122999448694105152,-647937823609671289,6137269817520382918>()) {
            case 1941388540:
               if (this.a.aK) {
                  if (!var1.contains(
                     (String)com.yiyiaddon.m.b.a<"sd7srdwlg4pvj","uL4BeLzsW5sS1yELQEwFFCeit1ykEua9BZc/FniVQ6tuzeL4",-9158866120191466065,-8959697197114090302,-6527247848747810599,-8433992199041053728>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1na93p42uz0r1","qAJdnHwqS2xSc6XoHm172TJBNy2JwPnetqQ29gHDBk4=",8895424777943012039,136777384753469084,-4656869848698181081,-3406162171719144177>()) {
                        case -1157039806:
                           if (!var1.contains(
                              (String)com.yiyiaddon.m.b.a<"s10dvqc4xpgwas","r8X9k970O074cq3eyKrsyEUOlPXGf0PR1nScsILWGzg1DfGQlamm4w==",-4385301921703996331,4832362919425191921,-4795162800455118649,-5943141506491706961>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s317ea9chk1inz","4f9BSp24V6WNELjGIJO68yxsrYJ4OLo1bRfg9xL497I=",7265309393302815867,3229893788596012321,-7774588593521063068,-5923621158558444862>()) {
                                 case -621873220:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (this.a.dg < 3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3vs91msfefm8k","qfWdjyozBF/p0uu5zDuGoXmIbh2f9uWsgKEa8NJv/ck=",-4780733867622759149,5924738008581426491,-8602702503420972362,-7678478522459960465>()) {
                        case -1748444088:
                           this.a.dg = 3;
                           this.L();
                           this.u(var1 + "");
                           switch ((int)com.yiyiaddon.m.b.a<"s8tzvth85pdb0","+HgEMP7rLBSuG7SvdXIeD2kflgw2y1AV3PFIyUJkwLE=",4407906449899455080,-6453071239238767212,-8611624763804330890,9220846086300238117>()) {
                              case -1784730051:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s38v1ulgyfdgwt","NLoLt4vuK7RRO6JABxdjc5V3tSG9T1cM/XIVQlr5RAE=",5788841384326140017,-7855937320900636922,-8730894714727836892,2082753679250046809>()) {
                     case -1503313364:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public void b(Minecraft var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s24q21hs8ummxl","YbArAhQkX7QMpFHmE9XRBxrHzccKTOMVIdzXu/Mn2JQ=",3513504861641344078,-3170538612757930928,-8975679285450917742,6885513259176419859>()) {
            case 1658138231:
               if (var1.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xqu3j3wzi8qm","3t8Cc2OAQoQ4BGsSnhyX91siPo2GN5S0sFvx+e1bSP4=",8779089300215374970,8509031548076711974,7663707933347798176,-6905106140528164166>()) {
                     case 357616416:
                        if (var1.level != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s12ib1klcbo77","hvAZ4rIPM4Z+2Llf6qcN90UfPPEGnDRlQ5xo+0Vmo3U=",-1283741215039890952,-385461302295863245,-8388693065208070853,-1558502020885544978>()) {
                              case -143382864:
                                 if (var1.gameMode != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3rvfyx792pfvn","wTOjyeZYo4rPRRnXvsgNdAtNizO62xg+M0YoocJ1Mmw=",1189437409255932524,-2429861136041940350,-8989141007323050182,-1328448544800159320>()) {
                                       case -687108989:
                                          if (var1.getConnection() != null) {
                                             if (this.a.aJ) {
                                                label182:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1bdlfq659g4lj","NXXdKe8jYFBNEypGYTLVA5TnFduGmOg2PFnoZVp0l0k=",3139145022869739972,7578410742598349627,3491418597357497500,8318050389423531066>()) {
                                                   case 1008598616:
                                                      if (c.eI()) {
                                                         return;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s3t9k3xfnie25h","99ixeyeUXpH5b3TChIRa9Zcn8bZXXaSjcp8EKR3CkaE=",-6919777915043666040,-4664403996194330606,-8424413982741982283,-4460499882378419426>()) {
                                                         case -1254490695:
                                                            if (c.eH()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s13r6kj2x2eftv","igfnf5eFBDT2mtTUQOo9xB9JUcwea0Q7mUOby2vWtW8=",5301660334487358700,-497964364825377937,-8267339153987333919,8277762588257918928>()) {
                                                                  case -345426443:
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break label182;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.G.clear();
                                             if (++this.cV <= this.a.dg) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3ul38x9agzgq1","hqmZ8mC7Fg6Vq4A46jKkv4G8EzbA9IluyJiAk8uSLQE=",-8305146489222210994,5198452405613863094,-2517077193237760459,-7578996025538672912>()) {
                                                   case -1068881468:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.cV = 0;
                                             this.a.a(this.a, this.G, this::u);
                                             if (this.a.a == com.yiyiaddon.e.e.b.a.范围自动扫描) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s30ivd9vw0h5hm","VLFQaIOifBOFjb1/cIvgqgC8Y4TKohRQsuYAPR7O3yM=",-6778943661392385135,2014127507955443516,8307192034375507291,4110691842054758615>()) {
                                                   case -102804000:
                                                      if (!this.G.isEmpty()) {
                                                         label176:
                                                         switch ((int)com.yiyiaddon.m.b.a<"sfoyjpts0876s","GQFZmKSOBtaImSYv/hmqPniZcNL0LWSRZ5vnqSee5z0=",-8369381370000795998,9008406610032332477,-4757007780731983820,-1674621738107878911>()) {
                                                            case 311502985:
                                                               this.G
                                                                  .sort(
                                                                     Comparator.comparingDouble(
                                                                        var1x -> this.s.player.getEyePosition().distanceToSqr(Vec3.atCenterOf(var1x))
                                                                     )
                                                                  );
                                                               Iterator var2 = this.G.iterator();
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1w2fhw41dtecj","DoWaLdUR/cPGdYQv3xaIOQt5IZwz2t9+U/o2YlJ1K0o=",5038955821887777759,3217066198424215308,-2735762343895238654,5498072611155549856>()) {
                                                                  case -2145670840:
                                                                     while (var2.hasNext()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2esj3ug3bu1mx","4b42mEEj2N+TV7Ad9G/DbTM7mK9yxNNYw7qntP4L//4=",-519054372897413349,1752463670323482719,-7035113448385022023,-7080120058159971976>()) {
                                                                           case 1479802372:
                                                                              BlockPos var3 = (BlockPos)var2.next();
                                                                              if (!this.b.contains(var3)) {
                                                                                 label188:
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s12u6gqunybsdn","LiamBMMjI3iFSD96mUtPQ96ytu2h4X1UcXem4TmYP18=",-4039314587009242931,-8417892315444130127,5495596188113196803,-1727755774250301308>()) {
                                                                                    case -633054852:
                                                                                       this.b.addLast(var3);
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s1os9pnsb7emjj","jErRhgPsxzi+geYjuhKKjvUvD3cu4x8fMstjLKkJMeg=",-8535409684564008636,-5840445715323404881,-1598592043076308713,5122838034554501778>()) {
                                                                                          case 25161637:
                                                                                             break label188;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }

                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3any1knawur2l","vzuQCxSG6AEUed8T6b8cs8rrU9fz0/69TV/Z4Dc4Ds8=",3387832216259566645,2647143305341521895,-3004188619094094867,6112278570298679622>()) {
                                                                                 case 1944783357:
                                                                                    continue;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }
                                                                     break label176;
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

                                             boolean var10000;
                                             label225: {
                                                if (this.G.isEmpty()) {
                                                   label172:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3d2j7tk11yr5i","waTdj1njwRXYVw8y4WgFUAxL7FZXk1mDewG0apC5bhI=",6503660601404751562,5526391575905536352,-574612961884029311,6399571254815643459>()) {
                                                      case 1375802865:
                                                         if (this.b.isEmpty()) {
                                                            var10000 = false;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s9ehluafdpa69","nLpUiHC7Mw89Ch2ubA3Fgka+x8gFsF77RhN57LaKaio=",5706426405845221363,2331493655896915179,-8329765642810124798,-610925827009801114>()) {
                                                               case 1128159603:
                                                                  break label225;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s2g3347ejs6pmk","6y3Fr+PNQ0zjHfTsylEUs523pWX4Z+vb7Mt+b1C2IFA=",2750323861121923526,-8352124338116159615,-2133345888031404454,-7097913175042855774>()) {
                                                            case 252798875:
                                                               break label172;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var10000 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s19ij5ogy2a4v9","5UYLby3n59fRpzZm3rX9sKSaoxd7CUOZlQcm46xCF0k=",5911930912716635701,-9158837020968639241,7407627772639887382,-7913006455994514772>()) {
                                                   case 1688423:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             boolean var7 = var10000;
                                             if (!var7) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3dqe4y7s9n98n","9bPNoN8kdqFy7BE7+JKoR4lpEsmEAKNOftC/UYgmcm8=",-1958019268815511750,8494713613353426403,-8371547231912587513,-7866129519853225201>()) {
                                                   case 767791500:
                                                      if (this.a.aF) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1yyiyrmwd1ln","65FYXwbCQfbrS7fNaSJrFgQyBazIKrLzA4Atsi1Otmw=",-1078055186826275805,-701983540422993205,6764810565983958238,4466486058444132011>()) {
                                                            case 171198280:
                                                               if (this.aC) {
                                                                  label132:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sdn251bs49oot","DLSa2dRuRgt3zYXpjmIYDuArff1jbYklbBCZeA+lWbU=",8120510893438627611,3479883529942762508,-2018667903132316271,-9115134571058668948>()) {
                                                                     case -1988148240:
                                                                        String var10001;
                                                                        if (this.a.a == com.yiyiaddon.e.e.b.a.准星精准指向) {
                                                                           label129:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1qrrd8p05gtpo","d9XzwMcStIwSzy2qFlgDSQo6YgD4zlDzcgz73nx3yCI=",8687821715934974322,-4015004985246623098,3966491043447377767,6216346846986664306>()) {
                                                                              case -205671203:
                                                                                 var10001 = (String)com.yiyiaddon.m.b.a<"s1bejcfm05rjxq","msJDzsO1//1RfyAZ3qAny9N8RBatCzBFJnCoqqXbOcHlgYLKFT7UdiXbRjZfQIRqrrrUHTg9",8808558775124554570,-1274125153024128420,4307131343107017136,-3176557656977836306>();
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2gog702dade3n","P/rlAJiSfuQwyPjEkU1kty+f1nQ8SFLlJ5c9XbzV41k=",-4965057996860148324,5011973943243803416,525225371680125053,-117690054459772922>()) {
                                                                                    case 1656086272:
                                                                                       break label129;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        } else {
                                                                           var10001 = (String)com.yiyiaddon.m.b.a<"szlpe6mco7ums","qqbypiRUvwGuNmnhPUoMsHwjQ6VTBQV78UocVrjmGLigh7yk3dVM+iDguTrn/v/5/2hZ6g==",-8153718434842910367,4820359500038920741,-2355434738926013252,-2108434071161638883>();
                                                                           switch ((int)com.yiyiaddon.m.b.a<"sfkznc76he6dm","mcZjVjTWuOySUrL5JP3OJHvmJdVxYEK6IN3uMlvsww8=",5889646671108019139,-7155643070754587481,-4149923151646396986,2140518841400822488>()) {
                                                                              case 620631491:
                                                                                 break;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }

                                                                        this.u(var10001);
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2jx7jcb51okoy","d2st7cykZY8dUIG2bkhgoKrCOA/PJAtsDXQy3ZEGqpk=",5032799423575947506,-8429263546927194849,4671992681004336211,4181820245696017484>()) {
                                                                           case -1013851842:
                                                                              break label132;
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

                                                      this.aC = false;
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.aC = true;
                                             InteractionHand var8 = this.a.a(this.a);
                                             if (var8 == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s19dmg2smtd8lf","AaGozX5rwi7zOHVO0fe/8OQcS5he0vtg6lyq9aq1srI=",5005257251913946962,6248292772374220078,2551563920066032249,1841964644078481504>()) {
                                                   case -1440130130:
                                                      if (!this.aB) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1902hwpsk4ndr","OfvFE3oKqgI9UXhHULo2ivNhVHZPFV30vfmC3/iYFys=",6039959340840226335,-3687140359325243085,2066070352493103244,-2342703091611785677>()) {
                                                            case 707721995:
                                                               this.aB = true;
                                                               this.u(
                                                                  (String)com.yiyiaddon.m.b.a<"s1xrmeiiamuaf4","JQqiwH3j5SqrkYXkWX+QKPCxdbWyJSJxyrNIabx3ES3vL5YvR2jD0q2bp3Kf6IUksi6w6kscw7/Gkobu37Dm4A==",9107223392606816712,-3666559035006219107,7571275399743744699,-7494683090201282497>()
                                                               );
                                                               switch ((int)com.yiyiaddon.m.b.a<"smxzyvjrfo6sr","ujvtA2ObxU0MVW9RRG10kz2EsabCOMjjY/Zq1e17Rd8=",-1579663951233363125,4053789257750020709,3047088137686385329,1012737800827885357>()) {
                                                                  case 886919202:
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.aB) {
                                                label163:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3iqm1g2xwgrng","8dim00gdalFfbyLyY9dqTuPn7NgsGdwDK/N5yZWhTPQ=",-9064604260469699843,5404337329658277013,-1112424071016179413,5760973132991981265>()) {
                                                   case -1078129275:
                                                      this.aB = false;
                                                      this.u(
                                                         (String)com.yiyiaddon.m.b.a<"s3r5r8w282yjri","v+y9BaA6JT9fPj6ZTSV0la/9YjpPbZnEORYmAQhiT6RUNRAEoVFKD7zfaiAZgiLFp4ofMoVm8+Qh9NyOg8QLTA==",-5486790774318391131,5103005956305012123,-1909789977739441993,4300677533190947704>()
                                                      );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1fcdsdwdkaqms","TLD/3q4foV6s/TVijBIrrhS3hteO74B4fY0dqVXwLyE=",-5747734180715353369,-6370147620231836860,-8893350981308643560,-7251449977133774259>()) {
                                                         case -32711901:
                                                            break label163;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             int var4 = this.a.dh;
                                             if (this.a.a == com.yiyiaddon.e.e.b.a.范围自动扫描) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3if0t9u0vlvr7","0IDlCiYUFlZPaoN8tWN5PfYunHUeYEmeu8Qhn4xHZxM=",3907653876628610773,-2969482741161393094,8887387623742642350,-5463608134569042455>()) {
                                                   case -1829714907:
                                                      int var5 = 0;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2j9d2prifby3f","pfDdu8GgwzxTorQFar0sxGGpdMU6aChlXF3qohr8z3Q=",7642657501126173563,1487665823979291380,7654430638681295906,-3512576245748112270>()) {
                                                         case 171207742:
                                                            label211:
                                                            while (!this.b.isEmpty()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1jogio3zafdlg","5L3vcaJW+T+FQuc2BqqzPvAz/P+aQafWxsOV1H7ooJI=",876511708439895935,-2706182345567798140,-9131133921227716424,-8378322046138629087>()) {
                                                                  case -1176171947:
                                                                     if (var4 != 0) {
                                                                        label150:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2t0l1rtep13ov","YKnQM825JsGR0lx4FBr7vwWycTiC9vKx06JKCFfrOtc=",-7947400277747221537,6238845502250659731,-6439158279102961960,-7516576170375735812>()) {
                                                                           case -256624236:
                                                                              if (var5 >= var4) {
                                                                                 break label211;
                                                                              }

                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2bivzsusz6moh","/+tllAjzglJx39VEbm8xf5PJm1MQu7QTJOREh7xblQs=",395917212231079132,5252701608323262450,-1688017666003336456,-713616310072856781>()) {
                                                                                 case 1964969967:
                                                                                    break label150;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     BlockPos var6 = this.b.pollFirst();
                                                                     this.a.a(var6, var8, this.a);
                                                                     var5++;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3hoh95m04iapz","7JznXs8DJL5LuqBfg6YHuiPdtI77xjmmkhkvcPqsZWw=",-3637090215082480667,838426726344252178,4030629679404028295,-7115171252362684698>()) {
                                                                        case -1817213805:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s13144pjfve8ii","fDAIg609Oh5T1qOaagvpundrZzpRpH447Z1Kje7JFDs=",-5845198404681208273,-1039823532918345404,-6407744462445346323,8249265490462664610>()) {
                                                               case -1582162162:
                                                                  return;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                if (!this.G.isEmpty()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1ww2a3d389i31","u5FCDBTA6ftLtyqUI6Vgt+uWoBI0kqPIJ86BuIxxKwU=",-4449083531567557163,-4644264838514859156,-8720528210502784475,-6919420877146456125>()) {
                                                      case -701676646:
                                                         this.a.a(this.G.get(0), var8, this.a);
                                                         switch ((int)com.yiyiaddon.m.b.a<"srwija7qnyn1y","OJaiZ1Z3EMXDvHfepzcnTypCKci+QRMYBruUn1zupsw=",2886790693797855025,8180413844894150058,-4643090484120682214,1654160115371839796>()) {
                                                            case -2056373995:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                return;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"sxm04dbnxwdro","i3FgA8bGJp7GkKyJIufHQ8MlWgfhDKsHN8jl/txa8sU=",2244489497415107610,3182516278088651400,-5856906447466111443,8229317338697560935>()) {
                                             case 2103739105:
                                                return;
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
   }

   private void ao() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)com.yiyiaddon.m.b.a<"s1g7y35ij8xxzq","9ZEKneQGxXGZW69nD75FX/96csGyxarfFKJdV5i26rBV28MdP2zUZHty3OGLdnKAgEogPgwB+PtPd2UT2Ig=",-7540223718827657587,6394589306107313794,-6342885618207483728,1047556861479792137>()
      );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"sc2pewetoo5la","HENSHu5LjreB6eoxRmk/x/LEsoQ6TehPPowIbRny52HHwg==",4092184710705971817,7172493972161009720,3708923791914415014,-3682594047509826985>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"s2tl1oetysu6qh","9OdEhRJCnJCRrjPjJAzo08EhB9SLWEXJoPVPL6GsbogwGciD",-2094673408702910485,-5443011850948112889,455006942813615560,-4458261991780724588>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"sbx86u3p4sz45","u07AoU1A11svnWlcTjTVuD51xzsSU6U000IJcB6l9CYG2M0LJsw=",768231084497228629,-5995596865953574962,7293887720723482709,-5386617314786661011>()
         )
         .append(j(this.a.a.h()))
         .append(
            (String)com.yiyiaddon.m.b.a<"s3dqzpcalb8aai","WTQ79UCROMP3jsOuFFNaq0L24oql1yPeQejPs26DNLs=",-103737797973279693,-4566828181711004630,783801758156375233,-6949523849575207886>()
         );
      var1.append(
            (String)com.yiyiaddon.m.b.a<"sc2pewetoo5la","HENSHu5LjreB6eoxRmk/x/LEsoQ6TehPPowIbRny52HHwg==",4092184710705971817,7172493972161009720,3708923791914415014,-3682594047509826985>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"s1wklyxe2mh4n1","10kqJZDesTL1REmfzKMjuAnPrjgxlae7OOTsO2FK46Was3NY",-6279132628468587347,3342723741650922126,-1769901465206746215,3281849674471360222>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"sbx86u3p4sz45","u07AoU1A11svnWlcTjTVuD51xzsSU6U000IJcB6l9CYG2M0LJsw=",768231084497228629,-5995596865953574962,7293887720723482709,-5386617314786661011>()
         )
         .append(N(this.a.I() + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s3dqzpcalb8aai","WTQ79UCROMP3jsOuFFNaq0L24oql1yPeQejPs26DNLs=",-103737797973279693,-4566828181711004630,783801758156375233,-6949523849575207886>()
         );
      if (this.a.a == com.yiyiaddon.e.e.b.a.范围自动扫描) {
         label27:
         switch ((int)com.yiyiaddon.m.b.a<"s374m7tto8o3fl","lWTdEg5SC0f5qzLZFHYxMebzraaph+ctQ9PgVWqM7jw=",4134927564375491994,-1186298004736002267,-8681463535499868942,-2476515774784102197>()) {
            case -91865595:
               var1.append(
                     (String)com.yiyiaddon.m.b.a<"sc2pewetoo5la","HENSHu5LjreB6eoxRmk/x/LEsoQ6TehPPowIbRny52HHwg==",4092184710705971817,7172493972161009720,3708923791914415014,-3682594047509826985>()
                  )
                  .append(
                     (String)com.yiyiaddon.m.b.a<"sh5yfg9mzwc6a","ICvruTDtLzJ8/ts77JBTzBnXNdjYYzqQmLp2eH2LQihan45f",6422795604540657453,-118648038099680978,-5520206836530129772,5580068058159476762>()
                  )
                  .append(
                     (String)com.yiyiaddon.m.b.a<"sbx86u3p4sz45","u07AoU1A11svnWlcTjTVuD51xzsSU6U000IJcB6l9CYG2M0LJsw=",768231084497228629,-5995596865953574962,7293887720723482709,-5386617314786661011>()
                  )
                  .append(N(this.a.df + ""))
                  .append(
                     (String)com.yiyiaddon.m.b.a<"s3dqzpcalb8aai","WTQ79UCROMP3jsOuFFNaq0L24oql1yPeQejPs26DNLs=",-103737797973279693,-4566828181711004630,783801758156375233,-6949523849575207886>()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s35rajl8y583gj","iJZlW5bKz48quWskAroU+PUcxJ8uYZNiLZtM2P7+taI=",7596259477899840197,-1937772970020752143,-4518274968109262714,-2686720494403216728>()) {
                  case 1545046678:
                     break label27;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.append(
            (String)com.yiyiaddon.m.b.a<"sc2pewetoo5la","HENSHu5LjreB6eoxRmk/x/LEsoQ6TehPPowIbRny52HHwg==",4092184710705971817,7172493972161009720,3708923791914415014,-3682594047509826985>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"s1mte8n1100uen","TRoVA0Qhuf9zKWMtt/Z9lPy7NNdFa69SDr4W38ypy50K+tQ4",-8927540896067905071,5177459540559638529,-4093169902212937647,7792451446723476866>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"sbx86u3p4sz45","u07AoU1A11svnWlcTjTVuD51xzsSU6U000IJcB6l9CYG2M0LJsw=",768231084497228629,-5995596865953574962,7293887720723482709,-5386617314786661011>()
         )
         .append(N(this.a.dg + ""))
         .append(
            (String)com.yiyiaddon.m.b.a<"s3dqzpcalb8aai","WTQ79UCROMP3jsOuFFNaq0L24oql1yPeQejPs26DNLs=",-103737797973279693,-4566828181711004630,783801758156375233,-6949523849575207886>()
         );
      StringBuilder var10000 = var1.append(
            (String)com.yiyiaddon.m.b.a<"sc2pewetoo5la","HENSHu5LjreB6eoxRmk/x/LEsoQ6TehPPowIbRny52HHwg==",4092184710705971817,7172493972161009720,3708923791914415014,-3682594047509826985>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"s38cjwrp7n91or","2Vygbi/69+fB5nu6kCS7iF2w/roR2EEPx7vJBO07ZtNzq+Oq",-4173530741244823833,-4563956603038629298,8500956352707376753,-3359282492815028419>()
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"sbx86u3p4sz45","u07AoU1A11svnWlcTjTVuD51xzsSU6U000IJcB6l9CYG2M0LJsw=",768231084497228629,-5995596865953574962,7293887720723482709,-5386617314786661011>()
         );
      String var10001;
      if (this.a.aG) {
         label20:
         switch ((int)com.yiyiaddon.m.b.a<"swmr98acl45ie","ELOrdO7FzVTndu4FAWHnregt2Onw07jgPeIvA0SNlzI=",-5695022040003422552,482251571638578819,2392393087945474214,-7921973040338736443>()) {
            case 648470970:
               var10001 = (String)com.yiyiaddon.m.b.a<"s38cufue6hos57","r5nyGjwuqwxmbs6739N0xk8NZOsG0POvQp747M74T4eJLBWTly/ArhIS",1984062550197764328,-6473855828244410772,-3385838679018859459,1052909255190972472>();
               switch ((int)com.yiyiaddon.m.b.a<"sff7yklq6m8o9","y4idbaF6aQT0ZVbz4Zta1D1HxB5k8+dyRgrkaEs31Pc=",-4895896551552283522,-3823490658434951859,6321983349671505632,3447868023669390744>()) {
                  case 267481770:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s1xypizn7nozz3","YayWdG4t3LTeotfq+16FlIe5iHPQHVPzE+CLD6jOt6cmynGWcRV7VMrL",687879348175907819,-8672702304832364075,5389399320912148863,1777682676239165651>();
         switch ((int)com.yiyiaddon.m.b.a<"saz6fturpdx4b","BHo+sL7rLvwvYin+kQRf0RW51XOnN5bFW5dz42WHGBE=",4156606685851949827,5728459593790757733,-2407725442848225822,8144875107921853101>()) {
            case 1685639589:
               break;
            default:
               throw null;
         }
      }

      var10000.append(var10001)
         .append(
            (String)com.yiyiaddon.m.b.a<"s3dqzpcalb8aai","WTQ79UCROMP3jsOuFFNaq0L24oql1yPeQejPs26DNLs=",-103737797973279693,-4566828181711004630,783801758156375233,-6949523849575207886>()
         );
      this.u(var1.toString());
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s3v0oku6xelhry","yWbH5jrw6jeoEuiPJ1yVdTFfMUcAtsJpO3xrVNXH+OmvAsm/",-4875631957832096979,5231489072311723443,-3156582459417019681,3496409565885202076>(),
         var1 + ""
      );
   }

   private void o(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s3v0oku6xelhry","yWbH5jrw6jeoEuiPJ1yVdTFfMUcAtsJpO3xrVNXH+OmvAsm/",-4875631957832096979,5231489072311723443,-3156582459417019681,3496409565885202076>(),
         var1 + ""
      );
   }

   private static String N(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.e.e.b(this);
   }
}
