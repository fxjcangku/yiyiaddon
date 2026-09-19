package com.yiyiaddon.l.g;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45;

final class h {
   private static final int tZ = 16;
   private final int ua;
   private final int[] aF = new int[1];
   private final int[] aG = new int[1];
   private final int[] aH = new int[16];
   private final int[] aI = new int[16];
   private final int[] aJ = new int[1];
   private final int[] aK = new int[1];
   private final int[] aL = new int[1];
   private final int[] aM = new int[1];
   private final int[] aN = new int[1];
   private final int[] aO = new int[1];
   private final int[] aP = new int[1];
   private final int[] aQ = new int[1];
   private final int[] aR = new int[2];
   private final int[] aS = new int[4];
   private final int[] aT = new int[4];
   private final int[] aU = new int[1];
   private final int[] aV = new int[1];
   private final int[] aW = new int[1];
   private final int[] aX = new int[1];
   private final int[] aY = new int[1];
   private final int[] aZ = new int[1];
   private final int[] ba = new int[1];
   private final int[] bb = new int[1];
   private final int[] bc = new int[1];
   private final ByteBuffer a = BufferUtils.createByteBuffer(4);
   private final int[] bd = new int[1];
   private final int[] be = new int[1];
   private final int[] bf = new int[1];
   private final int[] bg = new int[1];
   private final int[] bh = new int[1];
   private final int[] bi = new int[1];
   private final int[] bj = new int[1];
   private final int[] bk = new int[1];
   private final int[] bl = new int[1];
   private final int[] bm = new int[1];
   private final int[] bn = new int[1];
   private final int[] bo = new int[1];
   private final int[] bp = new int[1];
   private final int[] bq = new int[1];
   private final int[] br = new int[1];
   private final int[] bs = new int[1];
   private final int[] bt = new int[1];
   private final int[] bu = new int[1];
   private boolean fT;
   private boolean fU;
   private boolean fV;
   private boolean fW;
   private boolean fX;
   private boolean fY;
   private boolean fZ;
   private boolean ga;

   h(int var1) {
      this.ua = var1;
   }

   void jg() {
      GL45.glGetIntegerv(34016, this.aF);
      GL45.glGetIntegerv(35725, this.aG);
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1f607i3ov7tdq","msNvXhfBv1Uyd9JqIRb5MMfC+z0NzkhNqbrdO8eNws8=",1770511987546009550,-5668284829877301752,4009762815628322561,-4115566895985443525>()) {
         case 521240023:
            while (var1 < 16) {
               switch ((int)com.yiyiaddon.m.b.a<"s26l9guptv642l","zXUAl+GKrPGVGH3ohgMKBgs/vEy7ZG/IDrFuyk02ebU=",1220568944153274845,-3702952921859609814,4201432061781178315,-7922832033553225196>()) {
                  case 2061836132:
                     label71: {
                        GL45.glActiveTexture(33984 + var1);
                        this.aH[var1] = GL45.glGetInteger(32873);
                        if (this.ua < 330) {
                           label58:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ey40mt26wfkm","KGYNKXeLM8tp7Cwv2cXjOgjBiYEbR+iULGeOMmVqgYg=",1900704965092475247,7766251214113806143,8742387216242355230,3104314339845785933>()) {
                              case 657777444:
                                 if (!GL.getCapabilities().GL_ARB_sampler_objects) {
                                    break label71;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1khp92u3bc7w0","/uUM687o9Mjzugd5E6PoHrjaXVYEcJExO+ppomC80Zs=",-5585330424206259423,729408937023710525,-5232590190044969575,2358466080361783907>()) {
                                    case 1349997703:
                                       break label58;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.aI[var1] = GL45.glGetInteger(35097);
                        switch ((int)com.yiyiaddon.m.b.a<"s122sr7y4aa8ln","3dpyS9fzge+9mgnXrMrzZxPLSP2OfJ7+jieREvGB/aE=",6665591101880225069,836912828086937556,-4862622936865330532,2660441044982343138>()) {
                           case -446874546:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2giazbnh0wg16","atj9oXRm0vL9PXXq4L41b5IJ93+FtlpQLpy+V5TIFts=",-5133294835581873797,-8930442671201021207,-1342320781662692997,-2194244486858088130>()) {
                        case 1388460898:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glActiveTexture(33984);
            GL45.glGetIntegerv(34964, this.aJ);
            GL45.glGetIntegerv(34965, this.aK);
            GL45.glGetIntegerv(35368, this.aL);
            GL45.glGetIntegerv(34229, this.aM);
            GL45.glGetIntegerv(36010, this.aN);
            GL45.glGetIntegerv(36006, this.aO);
            GL45.glGetIntegerv(3074, this.aP);
            GL45.glGetIntegerv(3073, this.aQ);
            if (this.ua >= 200) {
               label48:
               switch ((int)com.yiyiaddon.m.b.a<"sspnc0il6zsdg","3eM4KKcrVbWTKN4fedcHBgx1AITKoKONbQx7w0qga3I=",-1806235726755546101,9097704415388361892,7029285639536051821,-2475589565327520636>()) {
                  case 1391657803:
                     GL45.glGetIntegerv(2880, this.aR);
                     switch ((int)com.yiyiaddon.m.b.a<"s16hdl9r3tbyjd","z9j0DkEaIzjOjM3Ye0z8bheZckqcpeH03h7/0GfrxfY=",7045946262213356782,2669591027429560107,-974371618691902349,2392575679553079942>()) {
                        case -1620637155:
                           break label48;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glGetIntegerv(2978, this.aS);
            GL45.glGetIntegerv(3088, this.aT);
            GL45.glGetIntegerv(32969, this.aU);
            GL45.glGetIntegerv(32968, this.aV);
            GL45.glGetIntegerv(32971, this.aW);
            GL45.glGetIntegerv(32970, this.aX);
            GL45.glGetIntegerv(32777, this.aY);
            GL45.glGetIntegerv(34877, this.aZ);
            GL45.glGetIntegerv(2932, this.ba);
            GL45.glGetIntegerv(2885, this.bb);
            GL45.glGetIntegerv(2886, this.bc);
            this.a.clear();
            GL45.glGetBooleanv(3107, this.a);
            this.fT = GL45.glIsEnabled(3042);
            this.fU = GL45.glIsEnabled(2884);
            this.fV = GL45.glIsEnabled(2929);
            this.fW = GL45.glIsEnabled(2960);
            this.fX = GL45.glIsEnabled(3089);
            if (this.ua >= 310) {
               label43:
               switch ((int)com.yiyiaddon.m.b.a<"s3ppy9k5ljtya5","Z9QbqR7XPnsQspzi+cdV4u1XyGEm7VskGR4TdM9jPPc=",7491963083925338940,-4629821928824670130,8116247583749897173,-8927961925506030690>()) {
                  case -1988476346:
                     this.fY = GL45.glIsEnabled(36765);
                     switch ((int)com.yiyiaddon.m.b.a<"s1j85n17qt5s19","P3GIGLxqDuGZkLNcZOXOUXhklr+q2h2zEqvCbsEVXGs=",9057414529137890344,392049056231184116,-7112905520673227464,2924853078568693739>()) {
                        case -1089318997:
                           break label43;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.fZ = GL45.glIsEnabled(36281);
            this.ga = GL45.glGetBoolean(2930);
            GL45.glGetIntegerv(35055, this.bd);
            GL45.glGetIntegerv(35053, this.be);
            GL45.glBindBuffer(35051, 0);
            GL45.glBindBuffer(35052, 0);
            GL45.glGetIntegerv(3328, this.bj);
            GL45.glGetIntegerv(3329, this.bk);
            GL45.glGetIntegerv(3330, this.bl);
            GL45.glGetIntegerv(3332, this.bn);
            GL45.glGetIntegerv(3331, this.bo);
            GL45.glGetIntegerv(3333, this.bq);
            GL45.glGetIntegerv(3312, this.br);
            GL45.glGetIntegerv(3313, this.bs);
            GL45.glGetIntegerv(3317, this.bf);
            GL45.glGetIntegerv(3314, this.bg);
            GL45.glGetIntegerv(3316, this.bh);
            GL45.glGetIntegerv(3315, this.bi);
            if (this.ua >= 120) {
               label38:
               switch ((int)com.yiyiaddon.m.b.a<"s288if23sop8bw","uZkpvW3XAnGF235w9wNJSZG/4V1xuooGw9Rcge6R4oE=",-3531056681238270722,-5933187220422568913,-8351767943558695829,5518039465360584266>()) {
                  case -1703253914:
                     GL45.glGetIntegerv(32876, this.bm);
                     GL45.glGetIntegerv(32875, this.bp);
                     GL45.glGetIntegerv(32878, this.bt);
                     GL45.glGetIntegerv(32877, this.bu);
                     switch ((int)com.yiyiaddon.m.b.a<"sdcsjh256fj14","7Ad7wspV2SheRZATvLCe37ZPhskRB3BkSMsQadrTlHY=",7240491374336972497,-941866050222738827,-8489434602687211698,-1653097308701358341>()) {
                        case -415498937:
                           break label38;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glPixelStorei(3317, 1);
            GL45.glPixelStorei(3314, 0);
            GL45.glPixelStorei(3316, 0);
            GL45.glPixelStorei(3315, 0);
            return;
         default:
            throw null;
      }
   }

   void kr() {
      GL45.glUseProgram(this.aG[0]);
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sgoxp51ol8tc0","0D/JxYINmewt5y4WcKnRUbSgg+ZXiRPUEvnUgHHTbvA=",-1661726315055950507,-1672671172909947011,1610463624246000258,3795873728517854863>()) {
         case 1695389871:
            while (var1 < 16) {
               switch ((int)com.yiyiaddon.m.b.a<"sxoifl3un5orb","zlftPYslhAuufSga7w28XhSFG2KGYetBiuzT85nwlb4=",-8095413888724728919,-6977986161220779008,-4900759349811743924,-4526712196600818663>()) {
                  case -1285472985:
                     label127: {
                        GL45.glActiveTexture(33984 + var1);
                        GL45.glBindTexture(3553, this.aH[var1]);
                        if (this.ua < 330) {
                           label114:
                           switch ((int)com.yiyiaddon.m.b.a<"s1k4n1eof53m6j","k/a06q1EOgKNsm62Pj85nclciu2bqDiZkhWorrhAJXw=",-4007661657863961198,-3644906587033122420,5584565899317354778,6385061847956522702>()) {
                              case 749484881:
                                 if (!GL.getCapabilities().GL_ARB_sampler_objects) {
                                    break label127;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1n4lt6bt3ll1m","q2gr024yZeNV4/B59WB4Yp8yWUCsWMGO91OpR4IfAY0=",7091835665805799349,-5664242413011213388,6916124118925056956,5501857868539221171>()) {
                                    case 1550910872:
                                       break label114;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        GL45.glBindSampler(var1, this.aI[var1]);
                        switch ((int)com.yiyiaddon.m.b.a<"s3006mtd2z591y","S5+6uKPm5AVcanF6mUrkUJf3XzhkUchRWjGx0+aMtJE=",2311847202951055513,1418707629251526975,-6045541224159510970,-1513050663752449405>()) {
                           case 1844904600:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"s32lpq0faz3767","IZlLQtuaU4z2EfUQ8eW9OHP7HcVGpcXupPUZ6D9LtX8=",-3932302287123355567,650594887010343308,6804112067303388213,4704246788887232645>()) {
                        case -1311732994:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glBindFramebuffer(36008, this.aN[0]);
            GL45.glBindFramebuffer(36009, this.aO[0]);
            d(this.aN[0], this.aP[0]);
            e(this.aO[0], this.aQ[0]);
            GL45.glBindVertexArray(this.aM[0]);
            GL45.glBindBuffer(34962, this.aJ[0]);
            GL45.glBindBuffer(34963, this.aK[0]);
            GL45.glBindBuffer(35345, this.aL[0]);
            GL45.glBlendEquationSeparate(this.aY[0], this.aZ[0]);
            GL45.glBlendFuncSeparate(this.aU[0], this.aV[0], this.aW[0], this.aX[0]);
            GL45.glDepthFunc(this.ba[0]);
            GL45.glCullFace(this.bb[0]);
            GL45.glFrontFace(this.bc[0]);
            boolean var10000;
            if (this.a.get(0) != 0) {
               label102:
               switch ((int)com.yiyiaddon.m.b.a<"s1rvc5lcb6zwlc","ixO+lRzQ4hW6wIKoYmNcLH0Q/DPgT6gDCHG1ivdZUZI=",-6581011793437551008,5436295804864009712,-3941978261070564847,3713428080440799793>()) {
                  case 2054079067:
                     var10000 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"sbb5rjmk3q4lf","oHyv9OTBSG8MYBWXwKAKZ87nC/PbitU6H1nEsGXHiMU=",8753167876961841699,-7480798294145372059,2369883693752497523,-3354517640683216982>()) {
                        case 1955272854:
                           break label102;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s23eemoylucbj7","QqFE1qpIgDjOl9Bna11DvL3HKBLFhyszBUJ0xSGQNW0=",3927270962146350081,1579964055031335559,-671011305229634346,-7406970500957545949>()) {
                  case 1455697214:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var10001;
            if (this.a.get(1) != 0) {
               label95:
               switch ((int)com.yiyiaddon.m.b.a<"sg8ca9tlmikb6","jhJ+Ta/ROsQ4r795ZvgkZSAhD2y5jiDgnJVXsY8kh8A=",-2521134132281426461,-3065282425014324765,-6073453429302169594,5434802313815625326>()) {
                  case -807266983:
                     var10001 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s3fwyh81qwz8yz","+TeG7eKnEN4AlnTJr1pt0w3+TUlvWkSKrQ6cfVffc8A=",-1985085022653150141,748794738044608819,3385712197113644291,-1367924001652719878>()) {
                        case -1070553574:
                           break label95;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s1g2dw5t1h78fb","464IhQFmbfBCeHmkhyYz00aqJUNV381qmgJeDBkioo8=",-637516396834795302,-4824152700428075864,4201638142905407585,-7421249745399080327>()) {
                  case 586187474:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var10002;
            if (this.a.get(2) != 0) {
               label88:
               switch ((int)com.yiyiaddon.m.b.a<"s23u45irpt986m","YVXPnia4KYkEOgUYH/SGxVo4Ga/Ula43i6CUKf1XGsc=",1764925821496153879,-847132788177386174,-465163916397575530,-4649511986685056987>()) {
                  case -87204592:
                     var10002 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s2pdefd6xt8z3z","fSiE8RhEBVG5S8geSEOLq8cU8KzGxTmH6fGUc77Xytg=",-6819529232433068441,3473780255838133063,5396912708632401190,4384526086411613>()) {
                        case 1815896724:
                           break label88;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10002 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s1nqcf98zf4riu","WAZgzoZcPftFjd721c9xqteWmylh62SIo8kPblea2Vo=",5902672008604858385,-5359613162261795871,2716599437874361956,2035055547400908221>()) {
                  case -211283848:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var10003;
            if (this.a.get(3) != 0) {
               label81:
               switch ((int)com.yiyiaddon.m.b.a<"s2zx2sbn1m0sg7","zD9SMmtFtPRmRi4yHUlZXNE2v+tyGNNegWt7Y2WTfsg=",4204255900489768392,808046600711437322,-8559392885529970834,8113658128047479660>()) {
                  case 769718388:
                     var10003 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"sh8u4waahdnrj","QoPwDFMDLFbW4qe1LHyuUjqW9cvNYS3HF2Yf+F0XJ00=",6481925640085567337,-9063743508828955484,3773622107777684373,3239339210981419090>()) {
                        case 1414934282:
                           break label81;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10003 = false;
               switch ((int)com.yiyiaddon.m.b.a<"sgrse9hc5i485","Rlfno6mqRIIwd1czPMQMAqb3Q+HOacCjLeUPAjaJHow=",-4412164457721345181,8561481973277829631,2905147225016011899,6603262474424005079>()) {
                  case -1697960292:
                     break;
                  default:
                     throw null;
               }
            }

            GL45.glColorMask(var10000, var10001, var10002, var10003);
            a(3042, this.fT);
            a(2884, this.fU);
            a(2929, this.fV);
            a(2960, this.fW);
            a(3089, this.fX);
            if (this.ua >= 310) {
               label76:
               switch ((int)com.yiyiaddon.m.b.a<"s2u9kfpv0kll12","lkT2Agb4rqEQzhY2bjWC/qAba9seFYg/8kC8serGSfg=",65444484902050621,-4293037403085940646,7164827884627950034,-3351246245087780225>()) {
                  case -1706888228:
                     a(36765, this.fY);
                     switch ((int)com.yiyiaddon.m.b.a<"s3toow9t679z1y","d5LBSR1NNN/UNRvw9RnMKh2MiGp1Ok8t8NX3fZOLM1w=",-7915350904250309054,7709691270580687485,-5617987986921318635,-4178609666077502581>()) {
                        case -1837265231:
                           break label76;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            a(36281, this.fZ);
            if (this.ua >= 200) {
               label71:
               switch ((int)com.yiyiaddon.m.b.a<"sjrzmnjq8dq1e","fo1v4bHeo839tbLz6yFbDPVQo2JPf+/KMqAM8cHqppk=",6509734518233243704,6293740588925008458,-7940682568101529506,4419963522132630169>()) {
                  case -1464122824:
                     GL45.glPolygonMode(1032, this.aR[0]);
                     switch ((int)com.yiyiaddon.m.b.a<"s3m66o8b0gv4xo","q0Y8ex3Ci46x/oE5T19XafJ3k9mD9f4KOw0Aa1PB82I=",5277643196030886404,-8475276718913402734,-8041492592959130634,-5763279813894404878>()) {
                        case -1130715801:
                           break label71;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glViewport(this.aS[0], this.aS[1], this.aS[2], this.aS[3]);
            GL45.glScissor(this.aT[0], this.aT[1], this.aT[2], this.aT[3]);
            GL45.glPixelStorei(3328, this.bj[0]);
            GL45.glPixelStorei(3329, this.bk[0]);
            GL45.glPixelStorei(3330, this.bl[0]);
            GL45.glPixelStorei(3332, this.bn[0]);
            GL45.glPixelStorei(3331, this.bo[0]);
            GL45.glPixelStorei(3333, this.bq[0]);
            GL45.glBindBuffer(35051, this.be[0]);
            GL45.glBindBuffer(35052, this.bd[0]);
            GL45.glPixelStorei(3312, this.br[0]);
            GL45.glPixelStorei(3313, this.bs[0]);
            GL45.glPixelStorei(3317, this.bf[0]);
            GL45.glPixelStorei(3314, this.bg[0]);
            GL45.glPixelStorei(3316, this.bh[0]);
            GL45.glPixelStorei(3315, this.bi[0]);
            if (this.ua >= 120) {
               label66:
               switch ((int)com.yiyiaddon.m.b.a<"s3hrbhh9sfonaf","mHs39ZdOxsvOjaDK0Ao74sbS7/ekQhxhb5gD8RIcPP4=",-7802684810652942345,-2446514812094320150,-460578362824706436,-3115426944798726296>()) {
                  case -1017380656:
                     GL45.glPixelStorei(32876, this.bm[0]);
                     GL45.glPixelStorei(32875, this.bp[0]);
                     GL45.glPixelStorei(32878, this.bt[0]);
                     GL45.glPixelStorei(32877, this.bu[0]);
                     switch ((int)com.yiyiaddon.m.b.a<"s1udaesnrgss9z","dCC9bHs+ozuaWuJRX+Pe5cjOLOl7sd7+itXHsFCc6dU=",471042597112921001,3284793842849892064,197995388402504925,-3798781790963932445>()) {
                        case 514472776:
                           break label66;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            GL45.glDepthMask(this.ga);
            GL45.glActiveTexture(this.aF[0]);
            return;
         default:
            throw null;
      }
   }

   private static void a(int var0, boolean var1) {
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f65xvflhwj4x","dEHQPF2lhP/p1Y94lk8R2d3+RoA+4IxsDUml7whDp9Y=",-8824289258942352442,2472441738088765260,3514874469272965493,4630325402480320565>()) {
            case 1464973304:
               GL45.glEnable(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s13vlaao27wyja","sQElk2olYoRepjzIq2vm3VQhIVnrtmrNV9z/IoAy2xU=",-8957985285001903960,-5524201907242574044,7241140573524632897,3677945057693815806>()) {
                  case 1111836938:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         GL45.glDisable(var0);
         switch ((int)com.yiyiaddon.m.b.a<"sgyhpktt7wgav","I99NIO/ZkxM+ZI4Ge4aIhDtV2EGvLdOIE0hOhNDgMyw=",-274265492958006245,1737987467292652734,3572231527511086464,-3678643957504926144>()) {
            case -1546343442:
               return;
            default:
               throw null;
         }
      }
   }

   private static void d(int var0, int var1) {
      if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s10j2eheiik3md","BD5hHJ2T56sHxva8TDCx99wrf+WsWrAR9UdZBc1VVOI=",-8111332085899152203,7611020348090029427,887550881178198409,-2982452701257758251>()) {
            case -1144383394:
               GL45.glReadBuffer(0);
               return;
            default:
               throw null;
         }
      } else if (var0 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ww6zefmnxprd","ncgFZiXAKDBEzTBz3xywO9IWZm9i33rMd/eaMcH/iC8=",-2456029203649962266,-7568066548869577914,2986018933056897400,-3655272583759038237>()) {
            case -1912231208:
               int var2;
               if (A(var1)) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s3kieec5lgo1ra","me7iVWqDLqsYVR4loXmdeJ2dZkh3/M7k+X0fyQw4gr0=",7765797251510415352,5474598362606106939,-8758536490226307695,5552316493687551551>()) {
                     case 863734504:
                        var2 = var1;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ahjbv49ur9d9","Dbw5D0J3QnKENkI55m4Ship0Virm5RKy/oqM6xnZ05Q=",-7624553131590274046,-279412481048443166,409232318328023028,3077633317540545858>()) {
                           case 1318623665:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var2 = 1029;
                  switch ((int)com.yiyiaddon.m.b.a<"s1uf991nmp20xe","BOJEn3Fmcs0QXuC5l/RPd4gumXhemrObeqkDr0VGXy0=",5101001318418559097,-6933963078652322058,-7414042813653805230,-6197076249980225497>()) {
                     case 830005318:
                        break;
                     default:
                        throw null;
                  }
               }

               GL45.glReadBuffer(var2);
               return;
            default:
               throw null;
         }
      } else {
         int var10000;
         if (B(var1)) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s3jl92j6p4ej3x","mPhD5EwpR/MxIlil2JuUH6ggoP3Vq6tjRkyAkpwngEc=",-4327729031552076077,5854526260902271707,-1155431499150542160,1970202181487718193>()) {
               case -787500048:
                  var10000 = var1;
                  switch ((int)com.yiyiaddon.m.b.a<"s1v4soj6csnzgk","uthXL2O4XjHoLhXQR+URKWh5gN4i7qSYOI/q/nHJGfE=",7859413111576075516,8770516721884023435,6562109332186191067,7282366762491218211>()) {
                     case -876114423:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 36064;
            switch ((int)com.yiyiaddon.m.b.a<"s2lxony7xctitm","iiF1CNGS6okLJ57d9N5WSBUIrbF/H9xaPv4Y0n8egEc=",3946355765589270435,6093996621926762191,2991532910847847627,-928757423045284162>()) {
               case 188778348:
                  break;
               default:
                  throw null;
            }
         }

         GL45.glReadBuffer(var10000);
      }
   }

   private static void e(int var0, int var1) {
      if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f8owebf7g5ln","Eeh7og0wcI1Y2mdIUrAuxLpNyu7lJ8ID9HBvoWAsOKY=",2510926215665332085,2278079471976059679,-6427376859328775413,6237686217727805946>()) {
            case 1504402304:
               GL45.glDrawBuffer(0);
               return;
            default:
               throw null;
         }
      } else if (var0 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s7vjttdqsl7l5","9wskkyZ+v+/f3IY9IK0UQXGssXSR5mdpIU/l0sInlo8=",3106654193328472160,306751101815300703,-6876137973211150266,-8605928821350694908>()) {
            case 18130446:
               int var2;
               if (A(var1)) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s4omvfqc5ucsd","Crn87rycBNESiV8BtHzi+7pbcUJQWxn2Ci6QKvxDam8=",7262773763417679180,-3786746361630495855,-1428666530416026531,6347765968659215028>()) {
                     case -1176841020:
                        var2 = var1;
                        switch ((int)com.yiyiaddon.m.b.a<"s249i3qys80m3y","ESZNlmUdr8qSuBFE3YIo/0+9W9qw6VkaE3geMMkOsx8=",8315478895301243301,-8549805606610568121,-4011593744397407112,-8156658553543453401>()) {
                           case 1525880160:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var2 = 1029;
                  switch ((int)com.yiyiaddon.m.b.a<"s9ur78io5vs3i","FcKwqqc5yXHSHK5bU+OC9umpF/HDNHqUegxYZXRaLO4=",-3833865133861186740,816825555784368238,830569027001482670,-8236973037209693952>()) {
                     case -1477086341:
                        break;
                     default:
                        throw null;
                  }
               }

               GL45.glDrawBuffer(var2);
               return;
            default:
               throw null;
         }
      } else {
         int var10000;
         if (B(var1)) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s3988dk8xpp6bj","YaRZkfBqMBUKyom8LaqWFKOMD9HMf1h9SXKq3Fh8w5M=",2451349362807109915,8104693971647093196,-1681527454403520662,863689508088336036>()) {
               case -403232564:
                  var10000 = var1;
                  switch ((int)com.yiyiaddon.m.b.a<"s3d58w47x3x77k","JhurN8bWX/A/sDeZhl1ThpXv7Pj7/pvN9f0C7ktx2tw=",-1677531683608627212,-6431285308483592012,-3360868394382535839,-6439618497732274334>()) {
                     case 1988274734:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 36064;
            switch ((int)com.yiyiaddon.m.b.a<"s1umbx302pffbz","WZeo5XUj/Un+PWXO/3YNNonDqyo7yBOy46UqkgVn1yo=",-3233308746408656604,-6060405240336613085,-7248379256920191673,-4165693021109980747>()) {
               case 63645620:
                  break;
               default:
                  throw null;
            }
         }

         GL45.glDrawBuffer(var10000);
      }
   }

   private static boolean A(int var0) {
      if (var0 != 1028) {
         switch ((int)com.yiyiaddon.m.b.a<"s11bu8xrce9e1u","0W/5BEPyzniisj44yfSe60TmU/W5dj61GtIHw2A8otI=",2162186201406491898,-3034142943450039342,8268899097318308713,-3481397870353223509>()) {
            case 81584966:
               if (var0 != 1029) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2cyzbwizjj0z2","DHkRruWbh1dpKImImUc8OSBwoRBx0AWiSbsxECAobwU=",3066911736644165580,-6271779625010422671,-4994462021628655166,4738701641228075592>()) {
                     case -1819347256:
                        if (var0 != 1030) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2v1fkxrkbexjx","432wOcpvMm1OD8uqb9718TBqVOfgW5UEUaO/JGQhBOE=",-578589213980830743,-8817944148826994942,-194440842223458303,3974259138521933116>()) {
                              case 1291736936:
                                 if (var0 != 1031) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s129vwpud924xk","KDl4Ait9ahf26hCx4wIi7ssu5agLkyMxVVyYK1k3kig=",4694030561251024970,6834677875790296718,-2268514862037881135,4997730529511773448>()) {
                                       case 184127500:
                                          if (var0 != 1024) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2megj80yubqbh","jjjRffRJ66zI6VncORvGl6B5RxHUQo6/887KjNkNAG8=",4335820710943629674,-3999725017404101279,-1274317246521779338,850337460751146703>()) {
                                                case 956582835:
                                                   if (var0 != 1025) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s18kqtsoshl254","c3aU5mFzKBMlfeZpVf8XWwgayMyIJp8G/xw2fbn1za4=",5334927057856640363,291092453575479211,-8560552240253067483,-96051902438359454>()) {
                                                         case -1721251415:
                                                            if (var0 != 1026) {
                                                               label40:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3t2c5o7w4lqi6","cbKxYbGR+q+rfAz/7U+0OIETAkzRiDqqYi7g4JsUH7s=",-7887640835177629825,87752482937479849,-8123344729723013373,2277654658340084950>()) {
                                                                  case -1842852022:
                                                                     if (var0 != 1027) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"stk00vj2eiqh5","UPLAvE5STZJ6w8m8ZuAt2C/tN1NwntrwJreyCT2eiGQ=",-6244014372440238221,-4424813540989393336,21674396078816748,-6396525526149590068>()) {
                                                                           case -1535851162:
                                                                              return false;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3ldbp7pgp8lkt","FcTZbQmo75HShPWPcC8VKDCf0B8W7KsBuGb40/K73Pc=",2957587583980932167,-6218556033135577295,3277354183073935,8598115409889563392>()) {
                                                                        case -985279001:
                                                                           break label40;
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

      switch ((int)com.yiyiaddon.m.b.a<"s656yst34au96","qRvGU1tho5ELJJs7oFJnsFFuQXqVZiHqjZ2og6kriSI=",-1043911507163893758,6721037545192383320,2259619612422102490,664463861966220540>()) {
         case 1886827945:
            return true;
         default:
            throw null;
      }
   }

   private static boolean B(int var0) {
      if (var0 >= 36064) {
         switch ((int)com.yiyiaddon.m.b.a<"supj6fu0mm9jw","cKBwVy5ZhHpAr44kFIQcWSVSvGyq7UuuJGKsfsEI/U8=",-1319075950722857627,3440090438657892951,875053533819009272,-4876312277428883616>()) {
            case 921533678:
               if (var0 <= 36095) {
                  switch ((int)com.yiyiaddon.m.b.a<"s28lloexxgohr3","QqqTRBtjne+CuMUOR3qbgUBCzP1JNM51/HeIb3kZWGU=",1388687935109206762,-1389880139356281931,1608638874612276014,-2931053823387924179>()) {
                     case -5777785:
                        switch ((int)com.yiyiaddon.m.b.a<"s1lf9ms68f868h","uwuO3qb5+xj2oF0EWT99oeE2zmqLJc8oyGiCYqZb0kg=",-9082866156767697209,-7929660910209423716,2277517586581776264,-2158271166578773821>()) {
                           case 726759017:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2ncth5s18vaii","kY+5g50Q/VGIZ8Z8hHXqT5aiIraXAWbNq88jVusLIzI=",2276640673019028999,-3668219294636980439,-1635766709177743570,8668225129731560634>()) {
         case -699269031:
            return false;
         default:
            throw null;
      }
   }
}
