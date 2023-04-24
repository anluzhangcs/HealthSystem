package org.graduate.domain;

import lombok.Data;

/**
 * @author: Zhanghao
 * @date: 2023/4/21-21:19
 * @Description 搜索条件
 */

@Data
public class SearchModel {

    private String name;

    private long pageSize;

    private long currentPage;
}
