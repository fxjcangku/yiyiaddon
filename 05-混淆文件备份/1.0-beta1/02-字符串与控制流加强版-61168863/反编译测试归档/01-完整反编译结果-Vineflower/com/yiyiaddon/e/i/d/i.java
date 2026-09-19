package com.yiyiaddon.e.i.d;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record i(int fx, String lI, String lJ, int fy, String lK, int fz, int fA, int fB, String lL, int fC, boolean bI, boolean bJ) {
   private static final Pattern h = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s1nzuy5jeu4r8g","VduZyvR2HeINkNSYZtxi/r+EFxyzuzExJRYLiHZ3yy8b8DYXiWSOnW+QmIiO32Qdbiv8YImTHo4CpGxdCXv2Mm7PNYxg9R17t1qoLTJAco4=",-8757263821112487629,7321826486333970564,4305351358262201630,2536565031512450142>()
   );

   public i(int fx, String lI, String lJ, int fy, String lK, int fz, int fA, int fB, String lL, int fC, boolean bI, boolean bJ) {
      if (fx < 0) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"srem0ttnbn3s8","q013Tk5OnCpNMIjE6gqPpOJ89oaweqdkDyl1U3e5Oz3Zzcu2P6bNP/lu2aXXaQJeWUXpkG2wYWmojP00ip8=",7422203697963951184,-8930598297229021681,4063182116035400677,-1879751751386268596>()
         );
      }

      lI = Objects.requireNonNull(
         lI,
         (String)com.yiyiaddon.m.b.a<"s1bsrn33ts617a","P3t3tFW/qJ7BjMQVJzMwo7VJPlq4MkBTxJ1w/RqHwvf7UUY4eMImJnsbEBxZ+LCRMz7YJ3S8XmbfGZQP51g=",6266427416364631587,8756475794910248550,-6488612990365416416,8589315034826249073>()
      );
      if (lI.isBlank()) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s1ujtgmhoo73l3","GloYwp0XoniqKdgNvKbNXdwOwxO7kXa2ZATSyind1pu9mfCfVPqY2pgKU9K5DPxgCEC/kklegG5xR7eKNKvsOlKNEbfv6v56",-4034347509951885773,-8013675348683803852,-163008158876238079,2343612355258322504>()
         );
      }

      lJ = b(
         lJ,
         (String)com.yiyiaddon.m.b.a<"suta49a9xbspu","yUvey59P/vECUNHCQjpj5EAcwRQKwyIqPG741LXoDtV64rlSjnP35CVdUJHtqR7L6N4f+mMWBdpdIsyrIEtIb40BIto=",1572082230645785189,6551666012698535125,3017466030058366735,-2146325418286137646>()
      );
      lK = b(
         lK,
         (String)com.yiyiaddon.m.b.a<"s3sf91v504k51a","2p4STftgv2IDr5KEeKUyu7UDU6+kygEyyq9mlnIv3rHuBgXFpmNaeSEJcYebu+UvYV6N1x0EWsoCRveUDNwDL9Z3B1tp5A==",4697319943174943840,9179996164129538425,4583633784608280793,-7616044348142492722>()
      );
      if (fy < 1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s1207ml7cj4dov","UW18C/2YqaWOwQAXNLYXfnWIzGClf/W/kNShG/LQrecLaCWHd4ts5wjTseFo44DsEdBPzWJtLTzn/BZz0bAvPw==",5439373274454971962,8370134566189093819,6413499759789170924,-2598641500727160882>()
         );
      }

      if (fz < 1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"svbo1x6h12ule","p6OGZ5jbZV5EdtLNFWQpFfVbC+PKHovZ2AUBaDbtRY67Zlmnph8Ri5HhMQc6jgjsxJeOYN3Qb8qTBbegrZ625tiGMVRk2bEizVo=",-3224174441533005453,6547417009621653421,5726331892038826024,7150083844493217726>()
         );
      }

      if (fA < fz) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"sh3lm1zpyfqs7","tBfB0EZQSCI75j7ieIKPgRIvzIAmwTjsGNOQXc3wJGTsulkFQ5SFaGLgbkT0WASntXjT5GbplQBxIYEIEPrgz1uhrPhqExj9C7ldZFEIqOah2zgZtyOhIgTsUcE=",-151203680256174912,4757938044313650101,722337711479097705,-630069124406967400>()
         );
      }

      if (fB >= 0 && fC >= 0) {
         if (lL == null) {
            if (fC != 0) {
               throw new IllegalArgumentException(
                  (String)com.yiyiaddon.m.b.a<"s2sf08mibqsqpt","1TQxucg+kiATmD8v6Y5OKO6cIIwKM4Yl4UO8WW6CFcxm1A/f4QRBrQAGPfm0BAyHSz6XPQ==",4709920690708943265,-7632692714034864322,3178844199318357590,-7110914696806898126>()
               );
            }
         } else {
            lL = b(
               lL,
               (String)com.yiyiaddon.m.b.a<"s3ueqa0xolehv","RB6ykM9hp6CBmL9NHSCJi2fjZ6kSxxHymv22p9djPLMOoo0bWoNRdHVoUxkrhWZtejSSeXV70Jlb57Lh/kKlMZRqH3XmhSIQSB2EtQ==",2780150356800877131,-7755657941868467483,1335531998853590595,8091644451522355493>()
            );
            if (fC < 1) {
               throw new IllegalArgumentException(
                  (String)com.yiyiaddon.m.b.a<"s8e9wwvsf4x4g","I2lyU2AX7mkbx73UM1iNccnuAnHpji1x6QJo83LBBn5OOFPZ64rrSlppWdMj9tp5UpBhTQ==",-5287581920338330754,-7113575858770224237,8295543988378095161,-449398721556938315>()
               );
            }
         }

         if (bJ && bI) {
            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"s1tt2wn652i3us","KSmvx1u8SyugVb/4adWc2XGw0k2abYNJiE1+DU7/BsZ2trE4/wd/fujoVZXOs6RlFFWEaCVA",750065375970485267,3072498860007302075,-2365004757217870234,-2981791440008630835>()
            );
         }

         this.fx = fx;
         this.lI = lI;
         this.lJ = lJ;
         this.fy = fy;
         this.lK = lK;
         this.fz = fz;
         this.fA = fA;
         this.fB = fB;
         this.lL = lL;
         this.fC = fC;
         this.bI = bI;
         this.bJ = bJ;
      } else {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s28eqhxeldpjgr","/FuLOo67gbs3X88kLQ0L6SsFfW19Y3Bd9/xW28SRiFHX/FhO4gshba5QXLXJef3c",7177543648619573256,-6755972362456041384,7677894418961707698,7419909301711165013>()
         );
      }
   }

   public i(int var1, String var2, int var3, int var4, int var5, int var6, boolean var7) {
      this(
         var1,
         var1 + "",
         (String)com.yiyiaddon.m.b.a<"s3ibmm12ozngyp","+UyqvKpX56o4HnXUXonvPleFkdCn41AheEfCRoozpcrkemOd2566QmX7pgC8G6cdILskdmC7Ht+yHHAoAXknwl0O+vJIcrrieP0NUA==",2390449310907864116,-2875148753213485724,8997065057788206029,105399234936980543>(),
         1,
         var2,
         var3,
         var4,
         var5,
         var6 == 0
            ? null
            : (String)com.yiyiaddon.m.b.a<"sp73ednsyha1w","SSJkLdJSTjiQO4l2hG8nsFe/nkMXnojGCf7lZCiraKXP+j0gm1CsJFm0uQa3upZW3WFHTDdyxOw=",3905130983245933095,-7588214137832367917,-8924096402798241864,5642401278104283433>(),
         var6,
         !var7,
         false
      );
   }

   public int be() {
      if ((String)com.yiyiaddon.m.b.a<"sp73ednsyha1w","SSJkLdJSTjiQO4l2hG8nsFe/nkMXnojGCf7lZCiraKXP+j0gm1CsJFm0uQa3upZW3WFHTDdyxOw=",3905130983245933095,-7588214137832367917,-8924096402798241864,5642401278104283433>()
         .equals(this.lL)) {
         switch ((int)com.yiyiaddon.m.b.a<"sq8uj556pbo9i","0gqp0sLSUpxxB3A10WvBb9mpF7LEgZecAWHKjCjc4+Q=",-7573718106290949349,-6847652418503263638,-2474942459811588892,-587168102683938762>()) {
            case 72009934:
               int var10000 = this.fC;
               switch ((int)com.yiyiaddon.m.b.a<"sryh411x04hdz","CzPYl4iALE3+BraOzfIF6azGSvjb1p2JTXgGC5XCQAs=",-6713891676949609255,-4992697271923054102,-2970121663802309532,-2637410632036065809>()) {
                  case 794172015:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s27i3wdap3ecrz","zDcdrdBYoKumj0ZGHaqLBO4UImbKw+4t2ceU+I6io6M=",3184604699778779193,-7317652439494678700,4544311404000782177,2439001207444922354>()) {
            case -763023121:
               return 0;
            default:
               throw null;
         }
      }
   }

   public boolean bv() {
      if (!this.bI) {
         switch ((int)com.yiyiaddon.m.b.a<"s20cj43ps7ioyx","Zr16gSYP/4Lv9SAmqQGZfrBshKxc8zZH5l0MGqXf+9s=",936388031364847420,3713852803718390856,7060668790630256010,2586937163659444608>()) {
            case -157815701:
               if (!this.bJ) {
                  switch ((int)com.yiyiaddon.m.b.a<"s21jhi23t020rj","+drj/+Fn9Qn3CRaXJtMSS4tyj/KDJZJmoBnNZsMd6fQ=",-1671804585529273784,7208121705338503257,-3334950665041163954,-2640971310739956795>()) {
                     case -1039855116:
                        switch ((int)com.yiyiaddon.m.b.a<"s15x2lx0zlmub9","WN5k8Ftd8hDyLyZws74kp0uJuddYmDf7/78n9tUt+N8=",-4366845111545603357,-3602344236023748290,1664131061594048923,3813667568057459930>()) {
                           case -1472821111:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1h3in710s63gc","gotwe7iC+60uAaUZZmO4BDytR/RRJW3eVZvW1rCebRU=",928507499350778408,7141972462855514984,3796926575290313127,4094981859144402414>()) {
         case -1771985615:
            return false;
         default:
            throw null;
      }
   }

   private static String b(String var0, String var1) {
      String var2 = Objects.requireNonNull(var0, var1).toLowerCase(Locale.ROOT);
      if (!h.matcher(var2).matches()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3i394x4th40wl","Xee0Zi2GBhGgDjhW2VgCRLW8lVfi5cn5G7QD6gLLyzA=",3116771523282853128,-1800420178371245546,431368993730159201,4315490224003915865>()) {
            case 1829875325:
               throw new IllegalArgumentException(var1 + var2);
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public int bf() {
      return this.fx;
   }

   public String bw() {
      return this.lI;
   }

   public String bx() {
      return this.lJ;
   }

   public int bg() {
      return this.fy;
   }

   public String bu() {
      return this.lK;
   }

   public int ba() {
      return this.fz;
   }

   public int bh() {
      return this.fA;
   }

   public int bi() {
      return this.fB;
   }

   public String by() {
      return this.lL;
   }

   public int bj() {
      return this.fC;
   }

   public boolean bw() {
      return this.bI;
   }

   public boolean bx() {
      return this.bJ;
   }
}
