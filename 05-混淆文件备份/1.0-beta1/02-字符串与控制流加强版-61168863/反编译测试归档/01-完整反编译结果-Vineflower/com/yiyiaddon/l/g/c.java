package com.yiyiaddon.l.g;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.Rect;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix3x2fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL45;

public final class c {
   private static final c a = new c();
   public static final int tK = 32;
   private static final int tL = 12;
   private static final float jW = 4.0F;
   private static final float jX = 2.0F;
   private static final int tM = 1024;
   private static final int tN = 2;
   private static final float jY = 16.0F;
   private static final String FY = (String)com.yiyiaddon.m.b.a<"s24edafnc8zvh9","d10npPbX1NcUupVv5x8HCsnbkDp109FzNwMRRcz/81Pk2kgbkgNIeJwZ",-3583890936456610564,1403264031042870100,6685448393585543040,237097161762734335>();
   private static final int tO = 0;
   private static final float jZ = 0.77F;
   private static final float ka = 0.5F;
   private static final float kb = 0.0625F;
   private final Map<String, Image> bp = new HashMap<>();
   private final Set<String> aX = new HashSet<>();
   private final ArrayDeque<c.b> b = new ArrayDeque<>();
   private final List<c.b> dv = new ArrayList<>();
   private final ArrayDeque<c.b> c = new ArrayDeque<>();
   private final Set<EntityType<?>> aY = new HashSet<>();
   private boolean fQ;
   private Map<EntityType<?>, Item> bq;
   private Image a;
   private float[] d;

   private c() {
   }

   public static c a() {
      return a;
   }

   public void a(GuiGraphicsExtractor var1) {
      this.dv.clear();
      if (var1 != null && (!this.b.isEmpty() || !this.c.isEmpty())) {
         Minecraft var2 = Minecraft.getInstance();
         if (var2 != null && var2.getWindow() != null) {
            c.b var3 = this.c.poll();
            if (var3 != null && var2.level == null) {
               this.c.addFirst(var3);
               var3 = null;
            }

            if (var3 != null) {
               var3.tP = 0;
            }

            int var4 = Math.min(var3 == null ? 12 : 11, this.b.size());
            ArrayList var5 = new ArrayList(var4);

            for (int var6 = 0; var6 < var4; var6++) {
               var5.add(this.b.poll());
            }

            int var16 = var3 == null ? 0 : 1;
            int var7 = var3 == null ? var4 : var4 + 1;
            if (var7 > 0) {
               if (!this.e(var2, var7)) {
                  for (int var18 = var5.size() - 1; var18 >= 0; var18--) {
                     this.b.addFirst((c.b)var5.get(var18));
                  }

                  if (var3 != null) {
                     this.c.addFirst(var3);
                  }
               } else {
                  for (int var8 = 0; var8 < 2; var8++) {
                     int var9 = var8 == 0 ? -16777216 : -1;
                     int var10 = Math.round(b(var8, var2));

                     for (int var11 = 0; var11 < var7; var11++) {
                        int var12 = Math.round(a(var11, var2));
                        var1.fill(var12, var10, var12 + 32, var10 + 32, var9);
                     }
                  }

                  Matrix3x2fStack var17 = var1.pose();
                  var17.pushMatrix();
                  var17.scale(2.0F, 2.0F);

                  try {
                     for (int var19 = 0; var19 < var4; var19++) {
                        c.b var20 = (c.b)var5.get(var19);
                        int var21 = var16 + var19;
                        var20.tP = var21;

                        for (int var22 = 0; var22 < 2; var22++) {
                           var1.item(var20.m, (int)(a(var21, var2) / 2.0F), (int)(b(var22, var2) / 2.0F));
                        }

                        this.dv.add(var20);
                     }
                  } finally {
                     var17.popMatrix();
                  }

                  if (var3 != null) {
                     if (this.a(var1, var3, var2)) {
                        this.dv.add(var3);
                     } else {
                        this.a(var3);
                     }
                  }
               }
            }
         } else {
            this.b.clear();
            this.c.clear();
         }
      }
   }

   public void ki() {
      if (this.dv.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s37tewtkn96qtc","dM5VPuJkDihRB5IHAHpu/G/VGo/sHY1E/TmWI6VNwnY=",-6497830035286214242,7552106008193995663,-267540576167925072,699710965023991407>()) {
            case 564563893:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1 != null) {
            label84:
            switch ((int)com.yiyiaddon.m.b.a<"soj7ewuy7nlt2","yOEikr4p1uO7bCv1UwzizDT/ude5L8m8HpMCsIcsWiQ=",-8918012700460490838,-3636473923662700458,-5593731156811231046,-163405126823653297>()) {
               case -1861532657:
                  if (var1.getWindow() != null) {
                     Iterator var2 = this.dv.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3o61jdwx0db38","kf0VlDMlaC8//JjD5OuJyXWYm5b18z8NJ12mAUpLXU4=",4577976782549702704,-5442275803406305436,-1085094939523205244,2175937401232750957>()) {
                        case 262819271:
                           while (var2.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1xdky38rf4z2a","ztAhaxg/nwuJl8HKDBNIHu84a6JljEPd8V1SPPTCi7s=",6574966349763452087,4737162240815089502,1905406513796130209,-2507047237980715992>()) {
                                 case 444536454:
                                    c.b var3 = (c.b)var2.next();
                                    c.a var4 = this.a(var3.tP, var1);
                                    if (var4 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s39fr1qg8ewo5s","je7pBJE3qhN6LX+3MigVHLJk6t14atOsr0L+2oLxlrE=",3650696409125479636,7551229516094832914,2089648362164955292,-2854409204869408828>()) {
                                          case 1696203902:
                                             this.aX.remove(var3.FZ);
                                             switch ((int)com.yiyiaddon.m.b.a<"s16zd3e1lodzgs","51MY3BAHPCtZVkKEhTm8vBL6b15z/Fg3uqA4THN01Is=",-2477098697756240619,6901479600191790480,400371169253842758,-1655920634421601949>()) {
                                                case 858605311:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var3.b != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s16fnkuv21w4n9","iXjV15Wh8MTRWt69VJMnWBY/6URv7RaOkOiuMeULEz8=",-5344084883222029307,-454302522923831158,6225073740344198193,1138214991907771866>()) {
                                             case -1233079510:
                                                if (!var4.gf()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3u92iet07e7z9","sVH8VI92L0b5TaddUdLf99LiU7FNPa2aHszGKzUGP7A=",-2751312350756040244,1420530127757509915,6254561715922289390,610620604165124875>()) {
                                                      case -2040814036:
                                                         var4.a().close();
                                                         this.a(var3);
                                                         switch ((int)com.yiyiaddon.m.b.a<"sb9vbmomctq8x","bK3+cGAUZk+ylDRs9W1dqVt53IAwXyvYIe/wNEryas4=",7112092492997759351,4595724323737922573,-555220491165210940,2566289614423264741>()) {
                                                            case 813253188:
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

                                       if (this.bp.size() >= 1024) {
                                          label57:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ktmk1gnf399j","qSuQvckLAhc6lTHsRI48s29+a4BAhf50DFISWI+bT+E=",6749700459691634432,-8997471220411580629,-6382154777917958221,-3292733743767561289>()) {
                                             case 861955284:
                                                this.b();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1pdxx6vbynr5m","tR5hhfZoVw1d2kxnfcC7zNIPcnin8XIb2MVQ3t25m10=",7388519135336015108,-7896483418726899488,-5882193502910210038,-1151308574361943774>()) {
                                                   case -1073606592:
                                                      break label57;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       Image var5 = this.bp.put(var3.FZ, var4.a());
                                       if (var5 != null) {
                                          label53:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2vkkfaxy1kww3","K0Oyfbmc5JAASk7OE5+d2YuHyNY4f55DIeznkdSc1Bs=",4362247818120486932,-5357441667373310894,-1826007290064744954,5230312007065371542>()) {
                                             case -558910846:
                                                var5.close();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2y78tlixr3e9k","CmNMYp4DflikeunvwDInfL8rwTNLpruaAuRWZhtG3bE=",-6754901267427259205,-8538660376892777146,1006332596922205993,764724403178452198>()) {
                                                   case 2046332840:
                                                      break label53;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s1ufwo9031ix50","7pRhT5pd7g4bSlcTJ6Mu8OrJaTjh/FB6lMteMzfeQ9M=",-8345104207645822918,171707327210646972,-2846961114112020246,-5865376028231165052>()) {
                                          case -1448938299:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.dv.clear();
                           return;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sg7ne7lt0380s","RWzKUuVyUx/wtpwhCRSuBsL/1s6BMWKjC+MiDGZrtdc=",-3998540653570732612,-5354612052558032077,722624020025859254,4112915070778242115>()) {
                     case -875780273:
                        break label84;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.dv.clear();
      }
   }

   public boolean a(Canvas var1, ItemStack var2, float var3, float var4, float var5) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zx217ij1b7u1","RbJoy8nQA5geTUVm97PjSPUAAzba1asta9Su00Comk0=",-6850347543362141187,3806578493419999838,-423014760150317223,197404787970674379>()) {
            case -97925845:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1en13hn3ir5cr","nNWhRRkGcZxveWQYJPk0QR4RI0jU0fXhqAmHN6edWE8=",5945774200441952813,-7805003345972711377,1419068311716511225,6491624377780200512>()) {
                     case -1177237851:
                        if (!var2.isEmpty()) {
                           String var6 = j(var2);
                           Image var7 = this.bp.get(var6);
                           if (var7 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sr15id1dffx9i","zz9/tEMMUWcqBKMhFYYs8RdH6RgECPtPG53UXH08/Lg=",-7147496010810800942,-1397192252748449266,-2039967510462925314,5950423238384788619>()) {
                                 case 288270796:
                                    this.a(var2, var6);
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           a(var1, var7, var3, var4, var5);
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s29y30w2msrkgw","5Itt/VEjANc8JItV3pAK1VoUEaCzGyMHRnzdzhbvR+E=",7596495446483255073,-1057276737629854663,-667769976231035158,5491389359567039976>()) {
                           case 1413766530:
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

      return false;
   }

   public boolean G(ItemStack var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sq4b3nfuu815k","Pzn4BxJAH/wlH7kvMwcni7gc6TD+Kq9gw8pGI+oX4VA=",3877911543434728285,-6869585984572997861,-1085063741474126967,1334930637228104098>()) {
            case 2026780694:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2iotmyawpi51c","maQ50TAD/ckQn6tBgBt4sjz1iYAVEEIUBnsbDhtskbc=",937943347331817753,-9063675391947341269,2380278830850168843,-3455358530445881212>()) {
                     case 348932143:
                        if (this.bp.containsKey(j(var1))) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2v3hsa08an4vk","2yzrMVr4kIeQtEuEaA0W8wOUuKltkUj3IpheH4cSaZc=",3331553496585878656,6147859239776443762,1325322997351046823,7353776302548124680>()) {
                              case 2115268923:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ymgiwbwssbxn","D6N3WIIV9qGtXOqvAZewIQqjy2KUrohiHtyxVphE1VU=",-2847095140509177058,-9013899736911120869,-4105607537122420537,-3403449669700323454>()) {
                                    case 2080437690:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2r7q66g8qh834","E2HEsI4SeOsPTEHpFHxD+fJ9xzYgfiJqfpaCut8h8dg=",-7408823910512047190,-6355306985064160670,-6653592231623952959,8581900182436127369>()) {
         case -1223113373:
            return false;
         default:
            throw null;
      }
   }

   public void e(ItemStack var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3k6ng9vrmq6m1","tNu1y88QMENiwllrFNq69gmEEOhUzUiCYtGK2DgF/8g=",-7653425123845811056,-505859910576390746,8434054354264021921,6022937268740349097>()) {
            case 1686802612:
               if (!var1.isEmpty()) {
                  this.a(var1, j(var1));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ipi8ofjiqia","OM/+MrQQjAywEce+z0bW3Se3P5u8r/0TLsKR7y2bRs8=",3635201425930938786,-980061361909963506,8310005626630519202,2858722460253206056>()) {
                     case 1815186494:
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

   public void a(EntityType<?> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3orf1bepoxjoe","UEaXgWFD5p6BpFcnCnBSYvYzRAMBak7pGzh2dbLvCo4=",-4609584225923756174,3330993957675194334,2351484692234156680,6095404830634687154>()) {
            case -1699229081:
               if (!this.aY.contains(var1)) {
                  Item var2 = this.y().get(var1);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3fcj391efmqu4","ZuGcMfTdqTsrjprhi+pwj0FrVdN+TLKN3zCAazuOR2c=",6867683042520022942,3157914749049012793,-6435136107390913856,767150884384399012>()) {
                        case -1125346976:
                           this.e(var2.getDefaultInstance());
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     Identifier var3 = BuiltInRegistries.ENTITY_TYPE.getKey(var1);
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sej4020fki0jz","YjmxGu/moqjtBm+Dl3dOetd5PgxinSWr/TiphGqkSf8=",2342255900249686951,-3900821919987999450,-5059313416814353773,2223873425570224187>()) {
                           case 1994222073:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.a(var1, var3 + "");
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nk7lqi2e2zba","7rt6TEJIU9y1z/DyrFVIjSZ4uQdUbLrd3j8xlNsI9Wo=",-2218471434975916934,6767285799539569653,233245605176853195,-9114042597736794567>()) {
                     case -1326130606:
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

   public boolean a(Canvas var1, Block var2, float var3, float var4, float var5) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sgif8cwn05hnv","4eUZrYpI2b9JmSze9MvoKUKNqc/kKBa9nb22g74zSzQ=",3288515046746624962,7340525595578289070,-6168184557057937958,3394366291735409887>()) {
            case 315288041:
               return false;
            default:
               throw null;
         }
      } else {
         Item var6 = var2.asItem();
         if (var6 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1jgrhtlsyqbwv","IVl+GXNwLF08HT7hpnhoc3fQl3flCOGIz6VHZ7P0vpo=",923823489646777835,1814550126230294787,5930687888894653727,6165671413369289849>()) {
               case 1803590103:
                  if (var6 != Items.AIR) {
                     return this.a(var1, var6.getDefaultInstance(), var3, var4, var5);
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ndlcg9flqbwk","Bem5YS3DwYKqjyUeZZdTS48MvSfXWgje6PIvlWpMCT8=",5424651187321237053,5799796706838738565,-2147015736873791821,-8664541378150788477>()) {
                        case 1477686749:
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

   public boolean a(Canvas var1, EntityType<?> var2, float var3, float var4, float var5) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sxhlvnvhf62o1","SfmX/OZJ7196njzT4FWFq17zNeR1twGuCKMY5Mppaw8=",8612634681917054426,-7985340400599415980,-8113015346737598395,3530071314127400217>()) {
            case -176700009:
               return false;
            default:
               throw null;
         }
      } else {
         Item var6 = this.y().get(var2);
         if (var6 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3vcry3x6ayr8v","glpBrfY2z+TvPBc0dIBMvLcuy2zXfu13QX7MviQgc1A=",6453312621790240963,-7056334917576721989,3924293340526598575,7202642171240557393>()) {
               case 1891696325:
                  return false;
               default:
                  throw null;
            }
         } else {
            return this.a(var1, var6.getDefaultInstance(), var3, var4, var5);
         }
      }
   }

   public boolean a(EntityType<?> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rfr0f53vizgj","YcozLzXHL6qnxSXVmtx9IqVC15v+9agfgdsJArSIIbM=",2351672228521797102,-798193340888798757,5023001282917081586,6317521498838328731>()) {
            case 1421188207:
               if (this.y().containsKey(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s59i1jxq3ysy3","MNssCuGMNpwAka6Te34FaluMw+YiY6ZvfgRn+DZVFOk=",-7337421063609803906,6059671263165405438,-6324422125824935236,-4096323496626139627>()) {
                     case 86954858:
                        switch ((int)com.yiyiaddon.m.b.a<"s3qsssbnmjvlgh","ENLfwwVp3JUyi+BMeFh9TB2Q6Ah5Lyh4/FmRUn4p2+U=",3231006333655359951,7826089783115165924,-6252574942644250033,5771979013997540478>()) {
                           case 622750334:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2m48c6d5dx4dq","edW28F0wwDatHevv4iqGWEQR7Rwya4rZlDKdlLTR9z0=",7478955016592361996,-6414649428578584947,2141305129341745221,-7719795357044833867>()) {
         case 1815558435:
            return false;
         default:
            throw null;
      }
   }

   public boolean b(EntityType<?> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fskgx4tgf10r","sKcQiWTLQ4JzDjs0GthKIfTC2kGbFLULvNYpPoszARY=",-3268753156905106090,1507846701441760122,9109439603272653005,-3428710981706404558>()) {
            case -1196539536:
               if (this.aY.contains(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3l23v1kfgodkl","er4hffS0lBkxM8IB5Ke1P9jhhy8yTo9wLG8K3HpWyLo=",-1473889535414258900,-3941263226707160461,-1996647805780434418,1866202038711713797>()) {
                     case -1740142243:
                        switch ((int)com.yiyiaddon.m.b.a<"s7krcg87l9k2r","CQHzFWbAsNtwvi1bxgS2Dx82JvA9bEIA8eXCfiFQsg8=",-8076685040504635088,1099604949643124534,1858397555612339929,-6594405592579509927>()) {
                           case 948694842:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2xxqwtg3l3tw5","TUnRxJzq6B4R8Lv1DDIubgc76YrV52ecGiqmQyDPhjg=",4193060350953034517,2787719027482592110,-1499753753752113265,-5589260948050401338>()) {
         case -1618195172:
            return false;
         default:
            throw null;
      }
   }

   public boolean b(Canvas var1, EntityType<?> var2, float var3, float var4, float var5) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"swir8fu26gdum","sTEPyd9J4LAjpPQob07l/PH1dV4rtkV7p2X/305oLYE=",-3342136655704297400,8287048063773951221,4983622352851179967,5582157347172401347>()) {
            case 1686276235:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2f9al8zl8rbu3","f4E7xwo5RYQ1gtzeOxm343ApxsrtNAQ7dBMZg2G8aNQ=",1233876660479730808,3219834959534620486,440013848584367010,-2978496037925006255>()) {
                     case -236290424:
                        if (!this.aY.contains(var2)) {
                           Identifier var6 = BuiltInRegistries.ENTITY_TYPE.getKey(var2);
                           if (var6 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"syj8s0z9i2nc2","fSW4HFYR/2HKA0zWUj4Wpm3+2cSK/LwVVwUDtNMwOD4=",6939412387250104999,3013685837148495076,7304644323355414168,-806512604356895118>()) {
                                 case 862398775:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           String var7 = var6 + "";
                           Image var8 = this.bp.get(var7);
                           if (var8 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2b08hxfb49bvz","R8qB4h4YRi1mWRKC8oZu3jECtTGzbsdF87NWModZyAE=",8052384611562502825,7333581006238425712,-1466375953091528224,3651159471409820690>()) {
                                 case -439467304:
                                    this.a(var2, var7);
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           a(var1, var8, var3, var4, var5);
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s39z5qg07tnj55","XahyLmj+8h7OZMiQoMZB4NDNHoHhgsM8DbiicOp+Otg=",-1432497685924452893,-661088136632256349,188242113499865615,-6155141909417229546>()) {
                           case 1140695591:
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

      return false;
   }

   private Map<EntityType<?>, Item> y() {
      if (this.bq != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3up4bhwt639p4","0nEPD7MOWVUt4U0an45v0ssNq3qh0LZllibMjM0/WTA=",-7428978409893289622,-3250453607287440168,-2565947525883715453,854875551725927627>()) {
            case 643565050:
               return this.bq;
            default:
               throw null;
         }
      } else {
         HashMap var1 = new HashMap();
         Iterator var2 = BuiltInRegistries.ITEM.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2gf4pyrxb1qgb","/gjYuvxRz34j5DyMhBT6JfdV4ZvElq5uvjL3PtQ/uS8=",-4313012001498354390,2240893327157736860,-7993887370530913032,451663829427914862>()) {
            case -1103241280:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s38vs5ccrfjend","zki/NxZY8Is6UkcD5SKTgbH4AaIcOY6zHXl3dCl/UEg=",6922531072514825216,1684827126223224803,2740620680764807871,2226065268960812462>()) {
                     case -792345719:
                        Item var3 = (Item)var2.next();
                        TypedEntityData var4 = var3.components().get(DataComponents.ENTITY_DATA);
                        if (var4 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"syfl7me6wtfnu","oRRhskeUBqQvWBP8Tt+vpRJUwn3qCKy21HjWrKEbZJ8=",-6263074583462353188,-6070291774607668341,-5683244197624168759,-1664959143296677701>()) {
                              case -1150229404:
                                 if (var4.type() == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"si9rj2n3j41zo","pAZmiR+YXua8vUIuUgvkFekLLSQH2gDd8prEhhI7MVE=",6367660298593100301,958350614907507616,4141718895935662822,1143856037530071434>()) {
                                       case -447621023:
                                          switch ((int)com.yiyiaddon.m.b.a<"s7t6ryxkbnwgb","geFagKCqu80ED6nxLEQQgqN/nV2k2i0tSMK0orAapOA=",8594679014964282042,1234296642772006273,1991067924870111378,-7095542873858318887>()) {
                                             case -766618526:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var1.putIfAbsent((EntityType)var4.type(), var3);
                                    switch ((int)com.yiyiaddon.m.b.a<"s1o1oxshpl39b9","OAVGFaWGD06SCn0JmlzDEsKtQdXrNHVX1PnAPSlHOcM=",131985882373117853,-6444315986657660823,-4317473199672999685,7871899341124694470>()) {
                                       case 364705918:
                                          continue;
                                       default:
                                          throw null;
                                    }
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

               this.bq = var1;
               return var1;
            default:
               throw null;
         }
      }
   }

   public void b() {
      Iterator var1 = this.bp.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"smp93dupfe46h","2L2DveUYf8ZbEhK6kOVJN/vW4fiOQJmSSf0OKv3+xAg=",-2636248860528239610,6878811244535719328,-8420361047590743114,-4420178236929253300>()) {
         case 534371256:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2qhq5ft854m0y","69vSKD7WxP7+2wjftNCD5e1GX91lh4fJRhjl1RNWe/Q=",3434959587646357316,7005070247415102285,-285086616755283553,-6628306021951528093>()) {
                  case -719643883:
                     Image var2 = (Image)var1.next();
                     var2.close();
                     switch ((int)com.yiyiaddon.m.b.a<"s2f6q3vs3o8l5k","ZWiuS+DSFacRk6VhLvlzsI+MPbGlUcr9KfDChptAgFE=",5947382329933539907,-1051223925119876149,-7582620392886791960,4111449792527139615>()) {
                        case 851456325:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.bp.clear();
            this.aX.clear();
            this.b.clear();
            this.c.clear();
            this.dv.clear();
            this.aY.clear();
            this.kj();
            return;
         default:
            throw null;
      }
   }

   public void close() {
      this.b();
   }

   private static void a(Canvas var0, Image var1, float var2, float var3, float var4) {
      float var5 = var1.getWidth();
      float var6 = var1.getHeight();
      var0.drawImageRect(var1, Rect.makeXYWH(0.0F, 0.0F, var5, var6), Rect.makeXYWH(var2, var3, var4, var4), SamplingMode.LINEAR, null, true);
   }

   private void a(ItemStack var1, String var2) {
      if (!this.aX.contains(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"si6l88ynank7f","N3Kt/WTEytczJIs/rBgR8+S8T5z6VzE2UusKFq2k6yQ=",-5499683159764100010,5341123376574779851,5956978867818837574,-3847613949199581554>()) {
            case -1792119644:
               if (!this.bp.containsKey(var2)) {
                  this.aX.add(var2);
                  this.b.add(new c.b(var1.copy(), null, var2));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1yqk0rv50iy1p","RffSd71mi4JHn3lsC4XAz5s5ind2XgGFdoqTVSJBI1U=",-1069098875560082371,7229354714203478811,-2507385518456176768,-2988844801743451810>()) {
                     case -954402925:
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

   private void a(EntityType<?> var1, String var2) {
      if (!this.aX.contains(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2db1urfjavo01","OaNZekK78vw9JUOSJ99hChNZfLIOOs+sM/DkchlNghw=",-2780240421355697269,678286633752588061,8763293881577478893,-7144220790797808585>()) {
            case 46641033:
               if (!this.bp.containsKey(var2)) {
                  this.aX.add(var2);
                  this.c.add(new c.b(null, var1, var2));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mmxrvmirlihw","TPQ3yoGgL55XG6QtcALouNJd22P5I/c0X0FUZxPr8mc=",-1394578082843699261,-8193032590901262103,-1713106327252891371,-48176986604608390>()) {
                     case 1361848014:
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

   private void a(c.b var1) {
      this.aX.remove(var1.FZ);
      if (var1.b != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13hgh2hsp8zah","iruqNlZlsxxJ/zZPGQK9ZT7Z9bVQp+VR8HmcNMAqVHM=",-5954871191099560161,-2634174182368084504,-7390711738795281257,1380274114134836768>()) {
            case 2088839180:
               this.aY.add(var1.b);
               switch ((int)com.yiyiaddon.m.b.a<"s269obxp878tdh","Z2KhEw/TFN8htHKNJdT/Bjfm5JepSP3Tnft0ztDoYtg=",-878920270385077638,-7238482536271112169,6789203249794047896,-3122466802249149518>()) {
                  case 1617346812:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private boolean a(GuiGraphicsExtractor var1, c.b var2, Minecraft var3) {
      try {
         Entity var4 = var2.b.create(var3.level, EntitySpawnReason.COMMAND);
         if (var4 == null) {
            return false;
         }

         EntityRenderDispatcher var5 = var3.getEntityRenderDispatcher();
         if (var5 != null && var5.<Entity>getRenderer(var4) != null) {
            EntityRenderState var6 = var5.extractEntity(var4, 1.0F);
            if (var6 == null) {
               return false;
            }

            var6.shadowPieces.clear();
            var6.outlineColor = 0;
            if (var6 instanceof LivingEntityRenderState var7) {
               if (var7.scale > 0.0F) {
                  var7.boundingBoxHeight = var7.boundingBoxHeight / var7.scale;
                  var7.boundingBoxWidth = var7.boundingBoxWidth / var7.scale;
                  var7.scale = 1.0F;
               }

               var7.bodyRot = 180.0F;
               var7.yRot = 0.0F;
               var7.xRot = 0.0F;
            }

            float var16 = Math.max(0.5F, var6.boundingBoxHeight);
            float var8 = 24.64F / var16;
            Vector3f var9 = new Vector3f(0.0F, var6.boundingBoxHeight / 2.0F + 0.0625F, 0.0F);
            Quaternionf var10 = new Quaternionf().rotateZ((float) Math.PI);
            Quaternionf var11 = new Quaternionf();
            int var12 = Math.round(a(0, var3));

            for (int var13 = 0; var13 < 2; var13++) {
               int var14 = Math.round(b(var13, var3));
               var1.entity(var6, var8, var9, var10, var11, var12, var14, var12 + 32, var14 + 32);
            }

            return true;
         } else {
            return false;
         }
      } catch (Throwable var15) {
         return false;
      }
   }

   private static String j(ItemStack var0) {
      Comparable var1 = BuiltInRegistries.ITEM.getKey(var0.getItem()).toString();
      Identifier var2 = var0.get(DataComponents.ITEM_MODEL);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3f8gz12fkvzgw","INQIRPgDNAgTBv1f6+unWrxuOJPL8ChttktAatjaQnM=",-8477782764841217103,952110429136849509,-8079602780596272506,-2035592774896330086>()) {
            case 1222677121:
               switch ((int)com.yiyiaddon.m.b.a<"s2447jnozx2rux","MDiuMlx+6hH7EN4lObjDcQ7Dq2QAvvuKJvkgeZF0Yms=",-8526953964730410982,5282677841821546949,-5145715840469007528,7337830224858474762>()) {
                  case 1114726413:
                     return var1 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var1 + var2;
         switch ((int)com.yiyiaddon.m.b.a<"s31qsk2n6ju7ta","ncZTVM8RpccbM2smwGAKIsdjw2ICayLARb1usaFzeHM=",-1000788921283976998,101673243110842356,-1266688464134867743,-3711619499094139294>()) {
            case -157689340:
               return var10000 + "";
            default:
               throw null;
         }
      }
   }

   private static float a(int var0, Minecraft var1) {
      float var2 = 428.0F;
      return Math.round(a(var1) / 2.0F - var2 / 2.0F) + var0 * 36.0F;
   }

   private static float b(int var0, Minecraft var1) {
      float var2 = Math.round(b(var1) / 2.0F - 40.0F);
      return var2 + var0 * 48.0F;
   }

   private static float a(Minecraft var0) {
      double var1 = var0.getWindow().getGuiScale();
      if (var1 <= 0.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s37grrk05cyunm","l+AOxXLuiz7QGpMuV9qHQZMozpSMFqEnIdX/YSjojVQ=",8356905114211228380,2583565850010962760,532583196965889204,-1176573281030128148>()) {
            case 1001443666:
               float var10000 = var0.getWindow().getWidth();
               switch ((int)com.yiyiaddon.m.b.a<"s2ozs7gsvm9rnf","xzR1ZD+b21g0SRMSkmMWkWCLuaaaqcsWKvjj2axUu8A=",-1936402543028000343,-6729461264234835730,-4921737726018635811,-3912128457564891662>()) {
                  case -92497831:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var3 = (float)(var0.getWindow().getWidth() / var1);
         switch ((int)com.yiyiaddon.m.b.a<"s3f411cg8c75ek","fIHmfnQKrzjTibzMkP0D0zhWj09pqO3C8CVfDz9Lf3Y=",-7269856627981580868,-8608634465836058671,-7921863438914073503,-8836179818210931279>()) {
            case -972925860:
               return var3;
            default:
               throw null;
         }
      }
   }

   private static float b(Minecraft var0) {
      double var1 = var0.getWindow().getGuiScale();
      if (var1 <= 0.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s85a1hll7m3b9","bTvtHfZ6c69XvQKTd82RaS0G4OtXRzRxlpmvjWLAep4=",-7822034569952073323,6833165152756136674,-3271629936628420427,-2772501552985048784>()) {
            case 1445634528:
               float var10000 = var0.getWindow().getHeight();
               switch ((int)com.yiyiaddon.m.b.a<"s1ppvu4dzeiedh","pgyTEaSlNjuhH1O3H+uklHD5G0aA59oEM24wMrLbCHo=",-460701270653370668,4756111041973311244,-6859850020873193717,-3314424595828371942>()) {
                  case 1726284438:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var3 = (float)(var0.getWindow().getHeight() / var1);
         switch ((int)com.yiyiaddon.m.b.a<"sucflzkwkapyp","NS9ezelDM0eJES2CKEsY9TctrJUQhme18XVFk44QViE=",-5043463240092403395,-1393001652696203504,-7439954705762152714,4842858543752208142>()) {
            case 428164360:
               return var3;
            default:
               throw null;
         }
      }
   }

   private static int[] a(int var0, int var1, Minecraft var2) {
      double var3 = var2.getWindow().getGuiScale();
      int var5 = var2.getWindow().getWidth();
      int var6 = var2.getWindow().getHeight();
      if (var3 <= 0.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bnrp9bngijyf","AJLnCGK66htHvcVD+bfLSoK68t1KJDrrghrlnX8MBU0=",-3631288903054943368,-4865472592020592616,803201061235008862,-342745852684481924>()) {
            case 303931812:
               return new int[]{0, 0, 1, 1};
            default:
               throw null;
         }
      } else {
         float var7 = a(var1, var2);
         float var8 = b(var0, var2);
         int var9 = Math.max(0, (int)Math.round(var7 * var3));
         int var10 = Math.max(0, (int)Math.round(var8 * var3));
         int var11 = Math.min(var5, (int)Math.round((var7 + 32.0F) * var3));
         int var12 = Math.min(var6, (int)Math.round((var8 + 32.0F) * var3));
         return new int[]{var9, Math.max(0, var6 - var12), Math.max(1, var11 - var9), Math.max(1, var12 - var10)};
      }
   }

   private c.a a(int var1, Minecraft var2) {
      if (!this.ge()) {
         switch ((int)com.yiyiaddon.m.b.a<"sbpkp9f6l7ljv","zltN8vPjXHBRLd4YMyDXFuTUan2FNjE6m3yE+F+lVsw=",7795574681199092904,-7831810895581614588,1911688981057115107,2176383318177010008>()) {
            case -1368029133:
               return null;
            default:
               throw null;
         }
      } else {
         byte[][] var3 = new byte[2][];
         int var4 = 1;
         int var5 = 1;
         int var6 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s16q844e3g8v0o","TWhUi69V/TJh2Kw4l5pM4yNN25Ab6bLVKX4WVK+GBcc=",-1218493988319386196,-2124357768681316736,5334558129323366303,346196928006321466>()) {
            case -731140314:
               while (var6 < 2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3l4hu31i3daj8","Kjm8qTKqD0Q/iVs7wuQ7PGZGUeLzrFhG1KRj+xBe1qM=",-908855706728625318,-7772867209438392689,3771540400499991998,9033552495066125971>()) {
                     case 1367397999:
                        int[] var7 = a(var6, var1, var2);
                        byte[] var8 = this.a(var7);
                        if (var8 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s20axiiak6bml7","TCUDx+9imS3mPGeAp1FLOdNPBkGwCWUogeUPA3Me6Kk=",4160324838190330400,-7370455755215112877,-1782578253439514821,79501407846461125>()) {
                              case 1625527817:
                                 return null;
                              default:
                                 throw null;
                           }
                        }

                        var3[var6] = var8;
                        var4 = var7[2];
                        var5 = var7[3];
                        var6++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2t4qwkcoohuoa","ZkUB0DO3r1caKt8KxGqxd8xadaV8KIzaMnNzdMjTRuE=",-6015360451168474260,-5246927344101341154,8888619189872100407,740097856234418730>()) {
                           case 1011719921:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               byte[] var16 = var3[0];
               byte[] var17 = var3[1];
               byte[] var18 = new byte[var4 * var5 * 4];
               boolean var9 = false;
               byte var10 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3a4s3ydrumj1w","LvFT45Qs2vbGfy1I2eGuYZRvn8u5B/t90Ods/1DeZFc=",8777008317172121430,2312076615917496367,-4505069889311385265,-4796845145716748585>()) {
                  case 1093204765:
                     while (var10 < var18.length) {
                        switch ((int)com.yiyiaddon.m.b.a<"symuqbolgi6qx","t7LDy+ehaiIf+Zd9lV6+vtHj8/mZ+gk55jui/+0XILI=",1580052241587807684,-4529263462057125037,2252503323740080399,-4866429256723610237>()) {
                           case 893989102:
                              int var11 = var16[var10] & 255;
                              int var12 = var16[var10 + 1] & 255;
                              int var13 = var16[var10 + 2] & 255;
                              int var14 = (var17[var10] & 255) - var11 + ((var17[var10 + 1] & 255) - var12) + ((var17[var10 + 2] & 255) - var13);
                              int var15 = 255 - Math.round(var14 / 3.0F);
                              if (var15 < 0) {
                                 label85:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2pc1hudpergap","pw/71+YotmcX83Vh5PxFuB9bBaSvwwifx/BsyCc+y0A=",6162410513872530471,-354652476376509580,8356629367308169944,6215035045916983645>()) {
                                    case -393689977:
                                       var15 = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3w02t5vghk74f","HVM7nQzGf76YoD7vjUdysQRbDKyj7t6IhLxGZsT75yU=",-9122094079455468643,-6941619557405885020,886522092737867984,1319973118878811272>()) {
                                          case -936779160:
                                             break label85;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else if (var15 > 255) {
                                 label81:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3tk7d25lgcbfr","Za5Ym2iLGnm5NzzC9ORD7ihxQrkuBJRJidqkRDxl7Tk=",1689346025150072250,-7169258625855978426,-4356164817537249003,4995876549952717731>()) {
                                    case -627090900:
                                       var15 = 255;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1oqkq7qlxn2t1","y9lBHRoNO9DbLOeguJesB7/fXHHzgpkuwE+BZm/WXMw=",-8017436466435983355,6927307254671025600,-4551390809090096660,-8772485252790713492>()) {
                                          case -554191126:
                                             break label81;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var15 > 0) {
                                 label77:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ov5isjqpdeoe","xc16b6vA83QkANTQ0/X3vKny0oxScERB15F6rOBVHoE=",5571942636538915920,-798633385252411414,-8729299190950791436,-2554438665409904888>()) {
                                    case 322088503:
                                       var9 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"sgo5jl678odxz","V7OMkx9ULlSXscvkp3Z1e8UUOw2aB31DWcq0jf/9FXY=",-9122223047276343463,8645512306896273905,2661953016240138745,-759681515142786986>()) {
                                          case 1469818026:
                                             break label77;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var11 > var15) {
                                 label73:
                                 switch ((int)com.yiyiaddon.m.b.a<"s24dbvlz9q3kee","POFsUitDOwmBzqgPGoGycNAarwUd2QsvmMXLrqJOIgk=",-3351986245933957240,-5975566884902342380,-2853011697791905993,2128540388670965305>()) {
                                    case -1808396226:
                                       var11 = var15;
                                       switch ((int)com.yiyiaddon.m.b.a<"s2o59b1bymlbco","/gFrmIskWjGsdlQsCr/OIM4GmmXr6lsVDv2o6xb8eGI=",5371087738736780939,-3343123127464194098,-8679825547675866302,-5053637044174521911>()) {
                                          case 1092567563:
                                             break label73;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var12 > var15) {
                                 label69:
                                 switch ((int)com.yiyiaddon.m.b.a<"s11vsaoww97rlt","ebsBeZaJWosNm9KTOUZs7wE/ig7u+HVBgldtO8m/dxg=",-4551699118558269222,-8560535292953781581,-819558786670864362,-7931707855945776214>()) {
                                    case 1933804236:
                                       var12 = var15;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3255o3ytsah0w","8UCAizfCZBp58DgBKzSewtR92B9TcJQZCCsEJIjZpLU=",-2152802301966391467,-8570271149771624105,-8963411869755558579,-4456783866698316283>()) {
                                          case 349075783:
                                             break label69;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var13 > var15) {
                                 label65:
                                 switch ((int)com.yiyiaddon.m.b.a<"svcmodxvn24ll","lML99ff/5KCAiNYXMVRIzBSO4Ucuz41qGohFpFGa3hg=",-6567511998973078031,1845823445765205043,-6231344474397950796,-4672066332453569672>()) {
                                    case -1411667486:
                                       var13 = var15;
                                       switch ((int)com.yiyiaddon.m.b.a<"s38v8x5wljffe6","7ue9Sa30icn1Dli/Y4ObOdqdz32f+Q8Q5IfwlGXvx4U=",2732842320772831305,5168000736160252262,7947107570120616199,-1847031541068978749>()) {
                                          case 1066229079:
                                             break label65;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var18[var10] = (byte)var11;
                              var18[var10 + 1] = (byte)var12;
                              var18[var10 + 2] = (byte)var13;
                              var18[var10 + 3] = (byte)var15;
                              var10 += 4;
                              switch ((int)com.yiyiaddon.m.b.a<"s1ritzf0x4ug35","VmJ6tMbbvSMJe5dAjSDxXQ2Rz0hTVwg3QYErchBPMjw=",-5253999996153568957,7858827149618377133,-6780929201170258120,-4495398439204122281>()) {
                                 case 1326899022:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     Image var19 = Image.makeRasterFromBytes(
                        new ImageInfo(var4, var5, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()), var18, var4 * 4
                     );
                     return new c.a(var19, var9);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private byte[] a(int[] var1) {
      int var2 = var1[2];
      int var3 = var1[3];
      int var4 = var2 * 4;
      int[] var5 = new int[1];
      int[] var6 = new int[1];
      int[] var7 = new int[1];
      int[] var8 = new int[1];
      int[] var9 = new int[1];
      int[] var10 = new int[4];
      int[] var11 = new int[4];
      boolean var12 = GL45.glIsEnabled(36281);
      GL45.glGetIntegerv(34016, var6);
      GL45.glActiveTexture(33984);
      GL45.glGetIntegerv(32873, var5);
      GL45.glGetIntegerv(35097, var7);
      GL45.glGetIntegerv(36010, var8);
      GL45.glGetIntegerv(36006, var9);
      GL45.glGetIntegerv(2978, var10);
      GL45.glGetIntegerv(3088, var11);
      int var13 = 0;
      int var14 = 0;

      try {
         var13 = GL45.glGenTextures();
         var14 = GL45.glGenFramebuffers();
         GL45.glBindTexture(3553, var13);
         GL45.glTexParameteri(3553, 10241, 9728);
         GL45.glTexParameteri(3553, 10240, 9728);
         GL45.glTexParameteri(3553, 10242, 33071);
         GL45.glTexParameteri(3553, 10243, 33071);
         GL45.glTexImage2D(3553, 0, 32856, var2, var3, 0, 6408, 5121, 0L);
         GL45.glBindFramebuffer(36009, var14);
         GL45.glFramebufferTexture2D(36009, 36064, 3553, var13, 0);
         if (GL45.glCheckFramebufferStatus(36009) != 36053) {
            return null;
         }

         GL45.glBindFramebuffer(36008, g.ef());
         if (GL45.glCheckFramebufferStatus(36008) != 36053) {
            return null;
         }

         GL45.glDisable(36281);
         GL45.glBindSampler(0, 0);
         GL45.glBlitFramebuffer(var1[0], var1[1], var1[0] + var2, var1[1] + var3, 0, 0, var2, var3, 16384, 9728);
         GL45.glBindFramebuffer(36008, var14);
         ByteBuffer var15 = BufferUtils.createByteBuffer(var4 * var3);
         GL45.glReadPixels(0, 0, var2, var3, 6408, 5121, var15);
         byte[] var16 = new byte[var4 * var3];

         for (int var17 = 0; var17 < var3; var17++) {
            var15.position((var3 - 1 - var17) * var4);
            var15.get(var16, var17 * var4, var4);
         }

         return var16;
      } catch (RuntimeException var21) {
         return null;
      } finally {
         GL45.glDeleteFramebuffers(var14);
         GL45.glDeleteTextures(var13);
         GL45.glBindFramebuffer(36008, var8[0]);
         GL45.glBindFramebuffer(36009, var9[0]);
         GL45.glViewport(var10[0], var10[1], var10[2], var10[3]);
         GL45.glScissor(var11[0], var11[1], var11[2], var11[3]);
         GL45.glActiveTexture(33984);
         GL45.glBindTexture(3553, var5[0]);
         GL45.glBindSampler(0, var7[0]);
         GL45.glActiveTexture(var6[0]);
         if (var12) {
            GL45.glEnable(36281);
         } else {
            GL45.glDisable(36281);
         }
      }
   }

   private boolean e(Minecraft var1, int var2) {
      this.kj();
      int[] var3 = this.a(var1, var2);
      if (var3[2] > 2 && var3[3] > 2) {
         byte[] var4 = this.a(var3);
         if (var4 == null) {
            return false;
         }

         try {
            this.a = Image.makeRasterFromBytes(
               new ImageInfo(var3[2], var3[3], ColorType.RGBA_8888, ColorAlphaType.OPAQUE, ColorSpace.getSRGB()), var4, var3[2] * 4
            );
         } catch (RuntimeException var8) {
            this.a = null;
            return false;
         }

         double var5 = var1.getWindow().getGuiScale();
         int var7 = var1.getWindow().getHeight();
         this.d = new float[]{(float)(var3[0] / var5), (float)((var7 - var3[1] - var3[3]) / var5), (float)(var3[2] / var5), (float)(var3[3] / var5)};
         return true;
      } else {
         return false;
      }
   }

   public void a(Canvas var1) {
      Image var2 = this.a;
      float[] var3 = this.d;
      this.a = null;
      this.d = null;
      if (var2 != null) {
         try {
            if (var1 != null && var3 != null) {
               var1.drawImageRect(
                  var2,
                  Rect.makeXYWH(0.0F, 0.0F, var2.getWidth(), var2.getHeight()),
                  Rect.makeXYWH(var3[0], var3[1], var3[2], var3[3]),
                  SamplingMode.DEFAULT,
                  null,
                  true
               );
               return;
            }
         } finally {
            var2.close();
         }
      }
   }

   private void kj() {
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s3fp4ebhcz5oaw","+mzr12p9TFSMWdktgZiE5Cke2eIKjphTyLVhGUpPvX4=",-8853970686260414461,-9188489820955318491,-7512368159078675483,5166624651457344079>()) {
            case -808851997:
               this.a.close();
               this.a = null;
               switch ((int)com.yiyiaddon.m.b.a<"s18j2vcl04g2h","2yG4UOzv3C94dkSi9/aZyGjMPdQPHxOvWX1OHDwz5ZA=",1932532265495744503,6583716394238607464,3139481430194681493,-6547483186957428531>()) {
                  case 2074255652:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.d = null;
   }

   private int[] a(Minecraft var1, int var2) {
      double var3 = var1.getWindow().getGuiScale();
      int var5 = var1.getWindow().getWidth();
      int var6 = var1.getWindow().getHeight();
      if (var3 <= 0.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s4mbgqragxang","iuHC1osHoAl9aIlMXz9wUZu48FrStQQkoGKD9YLr+i0=",-238011010517903159,6663880261733125785,-1312722492841736514,-4793978298494406526>()) {
            case 1560124367:
               return new int[]{0, 0, 1, 1};
            default:
               throw null;
         }
      } else {
         float var7 = a(0, var1);
         float var8 = b(0, var1);
         float var9 = Math.max(1, var2) * 36.0F - 4.0F;
         float var10 = 80.0F;
         int var11 = Math.max(0, (int)Math.round(var7 * var3));
         int var12 = Math.max(0, (int)Math.round(var8 * var3));
         int var13 = Math.min(var5, (int)Math.round((var7 + var9) * var3));
         int var14 = Math.min(var6, (int)Math.round((var8 + var10) * var3));
         return new int[]{var11, Math.max(0, var6 - var14), Math.max(1, var13 - var11), Math.max(1, var14 - var12)};
      }
   }

   private boolean ge() {
      if (!this.fQ) {
         label27:
         switch ((int)com.yiyiaddon.m.b.a<"s4o1pqv5a20q2","AryDmyWcKN8qdBYZXR7nx8Qr6GMWQgAPirQSYZBmV4U=",4784187029558361376,5308873841980006780,-4305014145198035179,-430915866583240211>()) {
            case -1686871406:
               Library.load();
               this.fQ = true;
               switch ((int)com.yiyiaddon.m.b.a<"s1txyowom5xe37","lzvDhI4gp+uCiHi7EvQvuyR6i+F/He8VkyCc+5520j8=",8523819038934859447,6524494323383301470,6218180555705207630,-1089802365163331541>()) {
                  case 904502572:
                     break label27;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (g.a() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dd970nsyb91o","/cUhVwRewRaJ6mAlpMpib4duVOd7uj7k85b4sLQ/Uds=",-3648320009479774094,9063757521776278667,-8789573232996164961,-1685712501377981152>()) {
            case 1247951373:
               switch ((int)com.yiyiaddon.m.b.a<"s296zf08snp2br","fmZrUOL5aUje4OxW/Gt7fr6huX9ePGX1g4P6UHAuVXY=",-678858347706891868,-6435947367523201836,1858556627916647994,8316588228146099055>()) {
                  case -878399144:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"soq5zekchd9rs","/W4sl2BuPLJMeDm56bzL8pwOx0mEQ9GcRgu0Z1d9Dz4=",372365250772142255,8398095264726892416,7386190199025386512,3353198183176240137>()) {
            case 1012909007:
               return false;
            default:
               throw null;
         }
      }
   }

   private record a(Image b, boolean fR) {
      public Image a() {
         return this.b;
      }

      public boolean gf() {
         return this.fR;
      }
   }

   private static final class b {
      private final ItemStack m;
      private final EntityType<?> b;
      private final String FZ;
      private int tP;

      private b(ItemStack var1, EntityType<?> var2, String var3) {
         this.m = var1;
         this.b = var2;
         this.FZ = var3;
      }
   }
}
