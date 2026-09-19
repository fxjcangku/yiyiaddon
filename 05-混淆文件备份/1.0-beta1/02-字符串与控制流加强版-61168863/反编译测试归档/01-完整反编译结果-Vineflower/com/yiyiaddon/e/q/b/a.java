package com.yiyiaddon.e.q.b;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.e.q.f.c;
import com.yiyiaddon.e.q.f.d;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class a {
   public static final int qj = 1;
   public static final int qk = 32;
   public static final int ql = 1;
   public static final int qm = 64;
   private static final String zw = (String)b.a<"s190lacwh6g7v4","Wzmuz0cEKL7y7D6RzZzm9wBuilcwN3ADKAeaxTHxvMQ=",-114501078706960112,3434922400139332959,-7026342803964902183,465125213312087263>();
   private static final String zx = (String)b.a<"s81vjucv1g6x0","mCcpl1JzulFt+Pf5zmDFqL3vYh0C2/qHMOBvK45F3V6PgYeN",5969670922145281638,-3392901178086122792,2215335578989880330,6038895457789686535>();
   private static final String zy = (String)b.a<"sry88paktubjy","ePUVi0mFb1/XzGq46B20Kv7gXLU5p/EFNKWYWjd0sWYM0aeZufDnBew2z84=",-8204611702015013888,-6743378577527293873,-3873008346467495343,-5476095396150847114>();
   public d a = d.LOCAL;
   public int qn = 1;
   public int qo = 96;
   public boolean eW = false;
   public int qp = 120;
   public c a = c.图书管理员;
   public com.yiyiaddon.l.d.a e = com.yiyiaddon.l.d.a.i();
   private final Map<String, List<String>> aA = new LinkedHashMap<>();
   private final List<String> cn = new ArrayList<>();
   private final Map<String, Integer> aB = new LinkedHashMap<>();
   private final Set<String> ar = new LinkedHashSet<>();

   public List<String> i(String var1) {
      return this.aA.computeIfAbsent(var1, var0 -> new ArrayList<>());
   }

   public void c(String var1, List<String> var2) {
      this.aA.put(var1, new ArrayList<>(var2));
   }

   public List<String> bp() {
      return this.cn;
   }

   public void o(List<String> var1) {
      this.cn.clear();
      this.cn.addAll(var1);
   }

   public int o(String var1) {
      return this.aB.getOrDefault(var1, 32);
   }

   public void e(String var1, int var2) {
      this.aB.put(var1, a(var2, 1, 64));
   }

   public boolean ay(String var1) {
      return this.ar.contains(var1);
   }

   public void l(String var1, boolean var2) {
      if (var2) {
         switch ((int)b.a<"s29crl3ju5csey","T+582WaTkWBsqZePanQs4aTNK1kvEZOzqDhBL4vea9Q=",-6229391317633750250,-2881423393264948706,-1459911715997410799,-5558193761901658202>()) {
            case -394738348:
               this.ar.add(var1);
               switch ((int)b.a<"s2wrxjh37k9zeh","bfjzMVty1iEDPfTla8prmapcSGhnljGy/FIKWw58tGU=",-8492049921957540580,3225891759104886763,-2046210185121610875,-4636815499175020853>()) {
                  case -1670755961:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.ar.remove(var1);
         switch ((int)b.a<"s1o7vm6sscb9sa","0eEzKxlvGLplMYckDfSNsXK5oUeJLGqd9VEMc58iBwo=",4784771143537241177,-1628791735631407605,6572161473491079177,-5303614987534809019>()) {
            case 1386667856:
               return;
            default:
               throw null;
         }
      }
   }

   public void d(JsonObject var1) {
      if (var1 == null) {
         switch ((int)b.a<"s2x2j0cadf7cv9","c8Yas0xeJdp9EZcbEdLGA98awpNIMDw0IGwFH0pA5j0=",7477919649512359129,9024342437113151939,-4864741369027129342,5076483152688447173>()) {
            case 866212313:
               return;
            default:
               throw null;
         }
      } else {
         this.a = a(
            var1,
            (String)b.a<"s2ovf9ptizmrr7","phqTNpkP8r/XUIRMM8TGTHCW3K+t4/bYLzzPUdlhPm13UyR/",3830645147809092537,332248734578915123,2877399497042670756,6154467906355245409>(),
            this.a
         );
         this.qn = a(
            b(
               var1,
               (String)b.a<"sx413sxibbcb9","lffHeleQURORiVuWk+BFf0l0ddoQNxFkpvE+3D6YtF+4Qt438jn4SnKbz0ZMEA==",8373032432772158837,-151303946108258391,3431232995816657202,3253169684065137738>(),
               this.qn
            ),
            1,
            27
         );
         this.qo = a(
            b(
               var1,
               (String)b.a<"s1wv0pj3nbcfra","y1j1lAXJ3DLBsCsBlEfk6nNHJnH4NyfUrlQ95PD+sV0zHPUG9512Rrvi",-3376209237445906051,1417070826711945913,5777581469041036062,-9011855266502294273>(),
               this.qo
            ),
            8,
            256
         );
         this.eW = b(
            var1,
            (String)b.a<"sgqyehfq5wx63","6ycYLRlxWLSSyNXIY7HLyVtXKfjruIJTUtJx0JnGWgWfzbZbkBm+XR6vOf4=",-1440518112979501797,5783516466121378380,-107640601235074438,126494563967897792>(),
            this.eW
         );
         this.qp = a(
            b(
               var1,
               (String)b.a<"s1l1hgvdg52myu","dOmoxFJOspcMhE5qaLIgbP4ab/yFSBqgK9ZNnKUYCfqlKGDTfKYJPI/9",-3586393243955039250,-3302979263333142203,83949034298803207,1562267024073657048>(),
               this.qp
            ),
            20,
            600
         );
         this.a = a(
            var1,
            (String)b.a<"s3s7pc678fid7c","Yj/H0eiwK5WXhFFf6zc2rN+ols6s8ZQltV8T7fNYTTX0O5KV",-4928486312230974189,-4383836193994462019,-4764603820985116643,5162222127601966856>(),
            this.a
         );
         this.e = com.yiyiaddon.l.d.a.a(
            var1.get(
               (String)b.a<"s2ey3jhu5sxn70","wbPf2rMlrzrYWbCkMYB5AzM8yUnvpGGad5ePj0ifMJ3JNVTKba4=",2764275784240856879,-4924470078340188487,-5612755368996951229,6072164436159789149>()
            ),
            this.e
         );
         this.g(var1);
         this.h(var1);
         this.i(var1);
      }
   }

   public void c(JsonObject var1) {
      var1.addProperty(
         (String)b.a<"s2ovf9ptizmrr7","phqTNpkP8r/XUIRMM8TGTHCW3K+t4/bYLzzPUdlhPm13UyR/",3830645147809092537,332248734578915123,2877399497042670756,6154467906355245409>(),
         this.a.name()
      );
      var1.addProperty(
         (String)b.a<"sx413sxibbcb9","lffHeleQURORiVuWk+BFf0l0ddoQNxFkpvE+3D6YtF+4Qt438jn4SnKbz0ZMEA==",8373032432772158837,-151303946108258391,3431232995816657202,3253169684065137738>(),
         this.qn
      );
      var1.addProperty(
         (String)b.a<"s1wv0pj3nbcfra","y1j1lAXJ3DLBsCsBlEfk6nNHJnH4NyfUrlQ95PD+sV0zHPUG9512Rrvi",-3376209237445906051,1417070826711945913,5777581469041036062,-9011855266502294273>(),
         this.qo
      );
      var1.addProperty(
         (String)b.a<"sgqyehfq5wx63","6ycYLRlxWLSSyNXIY7HLyVtXKfjruIJTUtJx0JnGWgWfzbZbkBm+XR6vOf4=",-1440518112979501797,5783516466121378380,-107640601235074438,126494563967897792>(),
         this.eW
      );
      var1.addProperty(
         (String)b.a<"s1l1hgvdg52myu","dOmoxFJOspcMhE5qaLIgbP4ab/yFSBqgK9ZNnKUYCfqlKGDTfKYJPI/9",-3586393243955039250,-3302979263333142203,83949034298803207,1562267024073657048>(),
         this.qp
      );
      var1.addProperty(
         (String)b.a<"s3s7pc678fid7c","Yj/H0eiwK5WXhFFf6zc2rN+ols6s8ZQltV8T7fNYTTX0O5KV",-4928486312230974189,-4383836193994462019,-4764603820985116643,5162222127601966856>(),
         this.a.name()
      );
      var1.add(
         (String)b.a<"s2ey3jhu5sxn70","wbPf2rMlrzrYWbCkMYB5AzM8yUnvpGGad5ePj0ifMJ3JNVTKba4=",2764275784240856879,-4924470078340188487,-5612755368996951229,6072164436159789149>(),
         this.e.e()
      );
      JsonObject var2 = new JsonObject();
      Iterator var3 = this.aA.entrySet().iterator();
      switch ((int)b.a<"se0si9653be6f","12l8OvNLVRC6emWScey+1AV+iO6bUQ6wq1LO7kbzKRY=",-291038714170902707,-5343672920185063302,-6503355035951027888,5689736404447084442>()) {
         case 529132869:
            while (var3.hasNext()) {
               switch ((int)b.a<"skkwlexec2it","p/OomVXRri+qPvHSdjdX6bBFRV39/ke43KfBREgoPBY=",936735092373097220,208056854114654284,-1155046733354745905,7608769032945614453>()) {
                  case 1485168209:
                     Entry var4 = (Entry)var3.next();
                     var2.add((String)var4.getKey() + "", c((List<String>)var4.getValue()));
                     switch ((int)b.a<"s1hcvpwenmesoj","AxsPTPK/HjVVpTLN5bb+tFyrAfOLPTIqNwYxLvQrOg0=",8532099336968493112,-1910303752618506900,-8814753351102223862,-3623144573765693436>()) {
                        case 1553766063:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var2.add(
               (String)b.a<"sry88paktubjy","ePUVi0mFb1/XzGq46B20Kv7gXLU5p/EFNKWYWjd0sWYM0aeZufDnBew2z84=",-8204611702015013888,-6743378577527293873,-3873008346467495343,-5476095396150847114>(),
               c(this.cn)
            );
            var1.add(
               (String)b.a<"snyn654resa1i","cciJe33NX0wIDBALRv9WTtt49eu72sfeFpEC8azmlFx/taFk",1527260303889900489,4947011761011062078,1246210592877946765,4786374743714321318>(),
               var2
            );
            JsonObject var7 = new JsonObject();
            Iterator var8 = this.aB.entrySet().iterator();
            switch ((int)b.a<"s2z34oc4euolf3","9I4MQTCS/UW/Xgc4Y1usGaWvfPxQJBlWm94p2UVBEJA=",2101093723114850468,-3559887530327806579,-668251692273681896,-8441360549573496074>()) {
               case 733800251:
                  while (var8.hasNext()) {
                     switch ((int)b.a<"skhu6uswr14wq","uMpoX8VkRqU4A5WnJMe2gW0av6q7kaI2Ub73GvbM8Gw=",-1115518881063361454,3763538484115832414,1642294053518089481,4336274533037779518>()) {
                        case -274239477:
                           Entry var5 = (Entry)var8.next();
                           var7.addProperty((String)var5.getKey() + "", (Number)var5.getValue());
                           switch ((int)b.a<"s1p8wl590wq3mf","pB1h0l93tC0xX5Zuzb4VRM8/FNk1g/VD9YSJN0uFvmU=",8138052194444047906,-1829255486771235332,-6894196243852632613,-9073332110874418828>()) {
                              case 218779427:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var1.add(
                     (String)b.a<"s81vjucv1g6x0","mCcpl1JzulFt+Pf5zmDFqL3vYh0C2/qHMOBvK45F3V6PgYeN",5969670922145281638,-3392901178086122792,2215335578989880330,6038895457789686535>(),
                     var7
                  );
                  JsonObject var9 = new JsonObject();
                  Iterator var10 = this.ar.iterator();
                  switch ((int)b.a<"ss0wsazjadncy","nv6C5YeHECgxdQoT2ZENsa8CebuqHXzDQi1HKF19Ato=",5550207721494556648,223326769721411366,1754653546780831039,-5361833510633165155>()) {
                     case -1438055437:
                        while (var10.hasNext()) {
                           switch ((int)b.a<"s1lnzeh4jdkonp","m3vAvXvEFLJSJhGjj8nRygTK916IRS8sGAExyYPcRng=",-8108640567868130863,-9063386662817882290,2430535585315524825,-451036436079599210>()) {
                              case -905616514:
                                 String var6 = (String)var10.next();
                                 var9.addProperty(var6, true);
                                 switch ((int)b.a<"s2izv12essrcad","tV0paELTESrr36ZAjZ58LlOHP5vPbMqnT4jWvOdjozg=",-6366975394008966288,7796575026765667730,7529593807303427495,-5030110110615016165>()) {
                                    case -625717131:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var1.add(
                           (String)b.a<"s386e0mfmzlrq7","Jtoa+35MdDHl1KIXRkxuAZ7tUXzyYTo1NRKbXhxQjRx45mVnk6Y=",3386214445010049534,7355290364505920848,7456639648555995770,-2266308648119735038>(),
                           var9
                        );
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
   }

   private void g(JsonObject var1) {
      JsonObject var2 = a(
         var1,
         (String)b.a<"snyn654resa1i","cciJe33NX0wIDBALRv9WTtt49eu72sfeFpEC8azmlFx/taFk",1527260303889900489,4947011761011062078,1246210592877946765,4786374743714321318>()
      );
      if (var2 == null) {
         switch ((int)b.a<"s2gnfacoa4pgnr","MoQHl7vNG0pCVdWx9g7Gi47zuhZeZwGO3dK4P3f9f5Q=",1822471028930396770,1024810703326388821,4672832504668949834,-3093916587032978563>()) {
            case -1293606905:
               return;
            default:
               throw null;
         }
      } else {
         this.aA.clear();
         c[] var3 = c.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)b.a<"s3uz5ngqkbr1ss","WCu0zgrUhTzuScvl1cRR9h1ExhYIF/8LES071nmiFl0=",-1297452457835617973,-217977617233034143,2570566774534320820,-3563926244087468996>()) {
            case 1209822838:
               while (var5 < var4) {
                  switch ((int)b.a<"s1t6eplruueksj","qNg6et1uYGuDn0JOrZB1SdIbNZQ02lIo7lWCbkD/vOQ=",2780955820695036561,-3609797117157844282,-4614945429564783297,2748348080610728774>()) {
                     case 730308817:
                        c var6 = var3[var5];
                        String var7 = var6.name() + "";
                        if (var2.has(var7)) {
                           label38:
                           switch ((int)b.a<"ss3iux62s9oy9","CJZ87+TEJAyKLPQOnS7ntTqQGQ5d/v5CCVQ1v04oVYM=",1039070151953389282,2547359130413894169,-854125113008056918,-7157598095397397542>()) {
                              case -1350617027:
                                 this.aA.put(var6.name(), a(var2.get(var7)));
                                 switch ((int)b.a<"s36tgnmgwijtlh","XB0L8aUlJpDuskGXRTSVEWjny9HwA6GxwcuWNh/ZvlQ=",-285107665654216236,8745575062783752258,-5824049916333154738,-3283293233396153088>()) {
                                    case 1692143368:
                                       break label38;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)b.a<"s3ghyap9r7dm1i","JFmX9VX9AthHGKfQSj4kb9n8yXVJ0KAZpMd4QzfIM6A=",4701585127407783,-1974816966865048119,6699488125545645825,-3392923544121959714>()) {
                           case -1104371137:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var2.has(
                  (String)b.a<"sry88paktubjy","ePUVi0mFb1/XzGq46B20Kv7gXLU5p/EFNKWYWjd0sWYM0aeZufDnBew2z84=",-8204611702015013888,-6743378577527293873,-3873008346467495343,-5476095396150847114>()
               )) {
                  switch ((int)b.a<"s21ye4ovs7hxo","/nN+FsJN89ZUL7MMfkFXZeUX28G+69Dc3bVCHEZrOBk=",5016714204617935609,-7329284399989546137,5826559770908937083,-6203485809374383235>()) {
                     case -147083724:
                        this.cn.clear();
                        this.cn
                           .addAll(
                              a(
                                 var2.get(
                                    (String)b.a<"sry88paktubjy","ePUVi0mFb1/XzGq46B20Kv7gXLU5p/EFNKWYWjd0sWYM0aeZufDnBew2z84=",-8204611702015013888,-6743378577527293873,-3873008346467495343,-5476095396150847114>()
                                 )
                              )
                           );
                        switch ((int)b.a<"s2cir91zoolxbz","gVmKjVTkaJT4xInQk9PzGD9x025DM7gYjqChWcqbI/E=",-5929906249126059701,-4487159543352032126,-2103009941470436455,8586465115800801616>()) {
                           case -1960097114:
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
   }

   private void h(JsonObject var1) {
      JsonObject var2 = a(
         var1,
         (String)b.a<"s81vjucv1g6x0","mCcpl1JzulFt+Pf5zmDFqL3vYh0C2/qHMOBvK45F3V6PgYeN",5969670922145281638,-3392901178086122792,2215335578989880330,6038895457789686535>()
      );
      if (var2 == null) {
         switch ((int)b.a<"s3ixzpbuxqgcdj","UWimKkskO1jt/+M3tLVKSV6JdJvgMePgg8yaWOplKuE=",-6704640205252231718,8329126470174174619,-2057981142289205114,3502874616186419353>()) {
            case -2066181103:
               return;
            default:
               throw null;
         }
      } else {
         this.aB.clear();
         c[] var3 = c.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)b.a<"s350nvbgathahf","5HAun435AVnvTQh5E/LI0gT93OolDJMFUh8P2Onnz6Q=",-9012337373945263610,-8333382709983157772,-5411166381298398526,8608698870866362427>()) {
            case -1956517797:
               while (var5 < var4) {
                  switch ((int)b.a<"s3et25rhi2evp7","+WZVsqZ3Zx9lQOXJgA/sDzCzhYR3Fno3C0tu+aProDE=",5136205425775251158,-4823874100873200023,2131450762681427110,-8229822479388565733>()) {
                     case 300293764:
                        c var6 = var3[var5];
                        String var7 = var6.name() + "";
                        if (var2.has(var7)) {
                           label28:
                           switch ((int)b.a<"s2t03tljy4fws4","g37aMfO3Q1ML/S1bczV67cudMK3m3Hfh20rC4ddc6fA=",-8409601322356891963,-4719036540025616100,-4868846064826399976,917391727406460395>()) {
                              case 159281916:
                                 this.aB.put(var6.name(), a(var2.get(var7).getAsInt(), 1, 64));
                                 switch ((int)b.a<"s301tel9ucl7fr","FVYlYuD/VO3Su7/5QLOqGbV/hOOfzkujEO7dldPUTGk=",-1956560266740397443,-5525540558314060671,8884540723493174060,7016293661963849516>()) {
                                    case 51343153:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)b.a<"s2fa675jpj3sm3","Hgv5jX4F9Hygx4D2apGSHUELbicRSba0EYagGrLMDcA=",-5985454269305252408,-5684836753179736565,-2134465532160897441,-5503352034840238361>()) {
                           case -1731145760:
                              continue;
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
   }

   private void i(JsonObject var1) {
      JsonObject var2 = a(
         var1,
         (String)b.a<"s386e0mfmzlrq7","Jtoa+35MdDHl1KIXRkxuAZ7tUXzyYTo1NRKbXhxQjRx45mVnk6Y=",3386214445010049534,7355290364505920848,7456639648555995770,-2266308648119735038>()
      );
      if (var2 == null) {
         switch ((int)b.a<"sqxo4gksrqopl","ZcQgVD16QUxb9wxxqRfbHu222vxtx2qQ76ihQ6/9/9M=",4798968681126013089,-7159948089127014642,545004553940193396,-4672201070073742550>()) {
            case 957634361:
               return;
            default:
               throw null;
         }
      } else {
         this.ar.clear();
         c[] var3 = c.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)b.a<"s59x7p9a63hkr","fTOi8XYnlnWDNPmforTgdgbwtFoAUeMD8n+bon77dU0=",-6744223833290004473,2963400306594233492,-8714002227916345397,3190705560827041939>()) {
            case -1146412812:
               while (var5 < var4) {
                  switch ((int)b.a<"set0deis715wd","cQFHr8HKAspY+t/27fTSgPbRXVQH8LKd6WxIt6TP/pE=",-6343913216827454345,-8236033952805081396,-6527928356350808968,2606229827400677028>()) {
                     case 415560645:
                        c var6 = var3[var5];
                        if (var2.has(var6.name())) {
                           switch ((int)b.a<"s2a3uqqzwbjmsr","zkMIzGGPa4LA/jetU81usI0fhxWP90XOcHqzd8okN94=",-5310084856704745792,-9032200571747512422,4814289770503927622,-2760294018928235107>()) {
                              case -1194089470:
                                 if (var2.get(var6.name()).getAsBoolean()) {
                                    label33:
                                    switch ((int)b.a<"so8mxqr14ld5y","9kpot6mLAqEPCInXzZUl+UoxATJG3FwlFJ8bRM9kQMk=",1654345646455232954,290803408516543397,6308624562547151799,2532908378503427109>()) {
                                       case 116479702:
                                          this.ar.add(var6.name());
                                          switch ((int)b.a<"s70a2casvth3k","xyocoSH8loxje320SXQkTAoTbzzi/mN8lBu/QT0wca8=",5023556346876538279,1073861760329403852,-1177592057554875797,5775053171055456927>()) {
                                             case -1670318848:
                                                break label33;
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

                        var5++;
                        switch ((int)b.a<"s3t3mih2w88nh5","9M2HbZD3P8O1kA+b7RU4g3KZ3zLfQMMG3ufp5odmF0M=",7294239795956291291,-3453963122448700262,-8429179053997880948,9122114857847041643>()) {
                           case -1686561163:
                              continue;
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
   }

   private static boolean b(JsonObject var0, String var1, boolean var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)b.a<"s1awiyntdc77wd","qOJo3L7Hq6Ja+7qJfBwN5/+MoIe1W+zMsH4y4ETHqkU=",5835489983101417629,3245905371865868166,1408228320444830731,-6562603493724253358>()) {
            case 971775618:
               if (var3.isJsonPrimitive()) {
                  switch ((int)b.a<"s9bq3xijkd2w8","rZAtXQnfLRKgPLc8Yv+jrl3aUxuptDvwySLSpA9ehUE=",-7911413225971704312,-7079987443952162774,2060875298416173586,-5808289060837055973>()) {
                     case -2103572012:
                        boolean var10000 = var3.getAsBoolean();
                        switch ((int)b.a<"s1t2d28m28jndr","WForRi5DzDxc8ogW8NuYf65HadJP2ZI329npIlZhHTY=",-3954638844965943329,3140375969096231132,4757009189902383441,8425089260336999597>()) {
                           case -1885365807:
                              return var10000;
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

      switch ((int)b.a<"s32os3lb7nr9h4","Pbi0GyvquxwerlCMgY5XRbgSYDzPa02jyc6rzNKLsww=",5431317596067463525,-1077172150622514376,683450780105459113,7257247335802238778>()) {
         case 1656246666:
            return var2;
         default:
            throw null;
      }
   }

   private static int b(JsonObject var0, String var1, int var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)b.a<"s1dpnvt1fxhtj0","T1QMMnUw1/d3NwsWE/yk0HBwZPog33z7kAWAtwOSj8s=",-7398390546385258438,-4275378156916737994,6072215196777674961,-8459498252900922041>()) {
            case 18445275:
               if (var3.isJsonPrimitive()) {
                  switch ((int)b.a<"s9akwmmtxbpjb","/xyxq/PLeTkBOtlOp6z5RRRu+fAioKFtyP1bDrapSPA=",1413244141615653451,-3001925888212826373,-6369961733693844580,9106908847005849632>()) {
                     case -1243207715:
                        int var10000 = var3.getAsInt();
                        switch ((int)b.a<"s110hjxcx3lxdy","wblwZLsnb1PvOksU4XMYHbGJKGndSspkgGSGlL6t6Xw=",4688050630344865130,-6102742738683635620,-9093235419782020775,-9189479737492931965>()) {
                           case -859629056:
                              return var10000;
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

      switch ((int)b.a<"s26ghe62j46i7v","NhXUbz9rdNvpBt973vtFpvExHxUBOY2JaGdh1QJWPSc=",7784262384431757789,6923136472656050397,-7195614309897510416,3733380624039354541>()) {
         case -114955681:
            return var2;
         default:
            throw null;
      }
   }

   private static JsonObject a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null) {
         switch ((int)b.a<"s3c3q865hsjam1","hZrxCzWMpp6XDWlrYo63KUUAPF8tSYsjqyIjP4d5+Rs=",2017703547929705149,-7288773013209112927,8596505155404954846,3302979411487394088>()) {
            case 225893453:
               if (var2.isJsonObject()) {
                  switch ((int)b.a<"s1a42oircxwzsa","iIBN3OrF++iNOLBgeQd8b7MNaEZnYTa4WYoxavCQnX8=",2570455809332964627,7992308439709041024,-5753232910811581930,-4108349636008246991>()) {
                     case -807934500:
                        JsonObject var10000 = var2.getAsJsonObject();
                        switch ((int)b.a<"s14dvxyo3nnzr5","+zaIl+H2PcrSkLAsgpnJHwpka7rbiRNtDCJQYFFsb8c=",7494952448036858770,-3566610999361043491,-2444618431527447733,-1274785846825831569>()) {
                           case 1138569416:
                              return var10000;
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

      switch ((int)b.a<"s3kollvkv6gvwx","cocG/t5/yuvGiZfJEI5+gbmhvhBd88/qvWalTniPIPM=",7974567993838452690,3592693484068731253,2097449770191830301,6632941149727945525>()) {
         case -1498302314:
            return null;
         default:
            throw null;
      }
   }

   private static int a(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static d a(JsonObject var0, String var1, d var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive()) {
         try {
            return d.valueOf(var3.getAsString());
         } catch (IllegalArgumentException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static c a(JsonObject var0, String var1, c var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive()) {
         try {
            return c.valueOf(var3.getAsString());
         } catch (IllegalArgumentException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static List<String> a(JsonElement var0) {
      ArrayList var1 = new ArrayList();
      if (var0 != null) {
         switch ((int)b.a<"s7w028w7wsely","mHGqUAgfS5mcoh7+ifuCRX2PMCE6WDbeCgkjSpdYHGY=",6684418002440834453,372211433177845366,6645020106670682588,-8717804096591086949>()) {
            case -1385728908:
               if (var0.isJsonArray()) {
                  Iterator var2 = var0.getAsJsonArray().iterator();
                  switch ((int)b.a<"srh9gib8cby3z","FFQKVVcAqJVbUnhaAEUOOUkznlFzRrgFg4ujMDKaVJo=",4672292629084321585,1175059669939984139,-5784824578170819028,-5238082925182026016>()) {
                     case 828135653:
                        while (var2.hasNext()) {
                           switch ((int)b.a<"s1p0oc71d1uj4v","gZcfiLtfoA5MLrilV4X0KX0/yRszaJgfDsLNdyK781Q=",9190692635168147709,3918077762201613685,8398564102397148986,-8602967098491003147>()) {
                              case -854015160:
                                 JsonElement var3 = (JsonElement)var2.next();
                                 if (var3.isJsonPrimitive()) {
                                    label30:
                                    switch ((int)b.a<"s2q6t22d1i6k9m","0cy6XjlPvbGKAphegJmFihatm3XEasOmyrZFrg1ZM50=",-7408720016890983057,2075770159924707354,8965650724132915679,8189747408152390772>()) {
                                       case -186458476:
                                          var1.add(var3.getAsString());
                                          switch ((int)b.a<"s19qpksyucnknd","+EcfSeisGQAvB3h0+ZJqi+5DE4Ole3C0yZhcwjcELRA=",-4448389085571606738,1034176606717526571,3757265697236742701,6276568613673245812>()) {
                                             case 1477910854:
                                                break label30;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"s1gu7cr88rmsji","xXqly0DPFwKACfQiSpf8kZDjyIdzJmTU6WcEdg6dRGU=",4111004795582111818,3997001873804344028,3680545754244893054,3450218247642218037>()) {
                                    case 145593171:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var1;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s31gr70png7z1t","obg6E5/enGtGn4odN0mSEfZKRWJyGomMFeDtyDrPNkI=",5993072828659483413,6076561843249330784,2087898628159881299,2946953736085527890>()) {
                     case 113645493:
                        return var1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private static JsonArray c(List<String> var0) {
      JsonArray var1 = new JsonArray();
      Iterator var2 = var0.iterator();
      switch ((int)b.a<"s1cz65cpphjkip","TUKzhASIQLEbNBzvm0/0AH3F9JeaHHlPdSXcB8iwWMY=",-7557912730812019438,6902550763356199040,-5190879382586322015,5386527909373748200>()) {
         case -2085143687:
            while (var2.hasNext()) {
               switch ((int)b.a<"s3rncc26q3gg4v","3o4A0H2L5EJ1r9IvxUHFS+VI7/TRb8fyqJzU9ud1yus=",1185682395856896732,-3368073712466353450,-8063721981678259167,-7782858011947807601>()) {
                  case 742506537:
                     String var3 = (String)var2.next();
                     var1.add(var3);
                     switch ((int)b.a<"s1d5sn9ivonpyb","v1uspfPFDJDlnRRRQP6dVPfEFGL5iMvqnupQvmrvh/8=",1650721376440953959,-6308103281124553699,2546477803190897022,-7182176639982085857>()) {
                        case 465066260:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }
}
