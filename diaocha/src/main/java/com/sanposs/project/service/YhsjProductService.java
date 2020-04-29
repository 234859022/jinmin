package com.sanposs.project.service;
import java.util.List;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.Service;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
public interface YhsjProductService extends Service<YhsjProduct> {

	Result addCyfwProductTp(YhsjProduct info,List<YhsjProductTp> dto,String attachpath,String deleteImgStr,String[] idList);

}
