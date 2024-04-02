-- ------------------------------------------------------------
-- sm_business_file | 基础附件表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_business_file;
CREATE TABLE IF NOT EXISTS sm_business_file (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    file_name VARCHAR(255) NULL COMMENT '文件名称',
    file_type INT NULL COMMENT '文件类型',
    file_size BIGINT NULL COMMENT '文件大小',
    file_key VARCHAR(255) NULL COMMENT '文件Key',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '基础附件表';

-- ------------------------------------------------------------
-- clearance_info_business_file | 清关信息与附件多对多关联表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS clearance_info_business_file;
CREATE TABLE IF NOT EXISTS clearance_info_business_file (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    clearance_info_id BIGINT NULL COMMENT '清关信息ID',
    business_file_id BIGINT NULL COMMENT '附件ID',
    remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '清关信息与附件多对多关联表';

-- ------------------------------------------------------------
-- sm_customs_audit | 海关审核表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_customs_audit;
CREATE TABLE IF NOT EXISTS sm_customs_audit (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    organization_id_of_submit BIGINT NULL COMMENT '审批提交组织ID',
    dept_id_of_submit BIGINT NULL COMMENT '审批提交部门ID',
    user_id_of_submit BIGINT NULL COMMENT '审批提交人ID',
    audit_type INT NULL COMMENT '审批类型',
    audit_info_id BIGINT NULL COMMENT '审批信息ID',
    audit_state INT NULL COMMENT '审批状态',
    remark VARCHAR(255) NULL COMMENT '备注',
    organization_id_of_audit BIGINT NULL COMMENT '审批组织ID',
    dept_id_of_audit BIGINT NULL COMMENT '审批部门ID',
    user_id_of_audit BIGINT NULL COMMENT '审批人ID',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '海关审核表';

-- ------------------------------------------------------------
-- sm_customs_clearance_info | 海关清关信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_customs_clearance_info;
CREATE TABLE IF NOT EXISTS sm_customs_clearance_info (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    transport_info_id BIGINT NULL COMMENT '运输信息ID',
    customs_declaration_info_id BIGINT NULL COMMENT '报关信息ID',
    customs_clearance_number VARCHAR(255) NULL COMMENT '清关单号',
    organization_id BIGINT NULL COMMENT '审批机构ID',
    inspection_result VARCHAR(255) NULL COMMENT '检验检疫结果',
    tax_and_fees DECIMAL(19, 4) NULL COMMENT '关税和税费',
    clearance_date DATETIME NULL COMMENT '放行日期',
    remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '海关清关信息表';

-- ------------------------------------------------------------
-- sm_customs_declaration_info | 海关报关信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_customs_declaration_info;
CREATE TABLE IF NOT EXISTS sm_customs_declaration_info (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    transport_info_id BIGINT NULL COMMENT '运输信息ID',
    customs_declaration_number VARCHAR(255) NULL COMMENT '报关单号',
    hs_code VARCHAR(255) NULL COMMENT 'HS编码',
    goods_value DECIMAL(19, 4) NULL COMMENT '货物价值（用于计算关税和税费）',
    estimated_transit_date DATETIME NULL COMMENT '预计过境日期',
    actual_transit_date DATETIME NULL COMMENT '实际过境日期',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '海关报关信息表';

-- ------------------------------------------------------------
-- declaration_info_business_file | 报关信息与附件多对多关联表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS declaration_info_business_file;
CREATE TABLE IF NOT EXISTS declaration_info_business_file (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    declaration_info_id BIGINT NULL COMMENT '清关信息ID',
    business_file_id BIGINT NULL COMMENT '附件ID',
    remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '报关信息与附件多对多关联表';

-- ------------------------------------------------------------
-- sm_goods | 货物信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_goods;
CREATE TABLE IF NOT EXISTS sm_goods (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    goods_name VARCHAR(255) NULL COMMENT '货物名称',
    goods_code VARCHAR(255) NULL COMMENT '货物编码',
    goods_unit_value DECIMAL(19, 4) NULL COMMENT '单位货物价值（单位：分）',
    goods_unit_volume DECIMAL(19, 4) NULL COMMENT '货物体积（单位：cm³）',
    goods_unit_weight DECIMAL(19, 4) NULL COMMENT '货物重量（单位：克）',
    goods_remark VARCHAR(255) NULL COMMENT '货物备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '货物信息表';

-- ------------------------------------------------------------
-- sm_iot_equipment | 物联网设备信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_iot_equipment;
CREATE TABLE IF NOT EXISTS sm_iot_equipment (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    transport_equipment_id BIGINT NULL COMMENT '运输设备ID',
    iot_name VARCHAR(255) NULL COMMENT '设备名称',
    iot_code VARCHAR(255) NULL COMMENT '设备编号',
    iot_key VARCHAR(255) NULL COMMENT '设备key',
    iot_state INT NULL COMMENT '设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）',
    iot_remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '物联网设备信息表';

-- ------------------------------------------------------------
-- sm_transport_equipment | 运输设备信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_transport_equipment;
CREATE TABLE IF NOT EXISTS sm_transport_equipment (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    equipment_code VARCHAR(255) NULL COMMENT '设备编码',
    equipment_name VARCHAR(255) NULL COMMENT '设备名称',
    equipment_type INT NULL COMMENT '设备类型',
    equipment_model VARCHAR(255) NULL COMMENT '设备型号',
    equipment_brand VARCHAR(255) NULL COMMENT '设备品牌',
    equipment_state INT NULL COMMENT '设备状态',
    equipment_remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '运输设备信息表';

-- ------------------------------------------------------------
-- sm_transport_info | 运输信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_transport_info;
CREATE TABLE IF NOT EXISTS sm_transport_info (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    user_id BIGINT NULL COMMENT '运输人ID',
    transport_equipment_id BIGINT NULL COMMENT '运输工具ID',
    transport_mode VARCHAR(255) NULL COMMENT '运输方式',
    send_organization_id BIGINT NULL COMMENT '发货公司ID',
    receive_organization_id BIGINT NULL COMMENT '收货公司ID',
    send_country VARCHAR(255) NULL COMMENT '起运国',
    receive_country VARCHAR(255) NULL COMMENT '目的国',
    send_date DATETIME NULL COMMENT '起运时间',
    estimate_date DATETIME NULL COMMENT '预计过境时间',
    actual_date DATETIME NULL COMMENT '实际过境时间',
    transport_state INT NULL COMMENT '运输状态',
    remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '运输信息表';

-- ------------------------------------------------------------
-- sm_transport_info_turnover_box_goods | 运输信息-周转箱-货物关联表（1*N*M）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_transport_info_turnover_box_goods;
CREATE TABLE IF NOT EXISTS sm_transport_info_turnover_box_goods (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    transport_info_id BIGINT NULL COMMENT '运输信息ID',
    turnover_box_id BIGINT NULL COMMENT '周转箱ID',
    goods_id BIGINT NULL COMMENT '货物ID',
    goods_num INT NULL COMMENT '货物数量',
    goods_to_box_user_id BIGINT NULL COMMENT '货物装箱操作员ID',
    box_to_transport_equipment_user_id BIGINT NULL COMMENT '周转箱装车操作员ID',
    remark VARCHAR(255) NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '运输信息-周转箱-货物关联表（1*N*M）';

-- ------------------------------------------------------------
-- sm_turnover_box | 周转箱信息表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sm_turnover_box;
CREATE TABLE IF NOT EXISTS sm_turnover_box (
   id BIGINT NOT NULL COMMENT '主键ID',
   create_time DATETIME NULL COMMENT '创建时间',
   create_user_id BIGINT NULL COMMENT '创建者ID',
   update_time DATETIME NULL COMMENT '修改时间',
   update_user_id BIGINT NULL COMMENT '修改者ID',
   data_user_id BIGINT NULL COMMENT '数据所属人ID',
   data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
   is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
   rfid_code VARCHAR(255) NULL COMMENT 'RFID编码',
   turnover_box_name VARCHAR(255) NULL COMMENT '周转箱名称',
   turnover_box_volume DECIMAL(19, 4) NULL COMMENT '周转箱体积（单位：cm³）',
   turnover_box_container DECIMAL(19, 4) NULL COMMENT '周转箱容积（单位：cm³）',
   turnover_box_weight DECIMAL(19, 4) NULL COMMENT '周转箱重量（单位：克）',
   turnover_box_state INT NULL COMMENT '周转箱状态（1：闲置中、2：使用中、3：废弃）',
   turnover_box_remark VARCHAR(255) NULL COMMENT '周转箱描述',
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '周转箱信息表';

-- ------------------------------------------------------------
-- sys_dept | 系统部门表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    organization_id BIGINT NULL COMMENT '所属组织ID（SysOrganization表主键ID）',
    user_id BIGINT NULL COMMENT '部门负责人ID（SysUser表主键ID）',
    dept_code VARCHAR(255) NULL COMMENT '部门编码',
    dept_name VARCHAR(255) NULL COMMENT '部门名称',
    dept_phone_number VARCHAR(255) NULL COMMENT '部门电话号码',
    dept_address VARCHAR(255) NULL COMMENT '部门地址',
    dept_introduction VARCHAR(255) NULL COMMENT '部门简介',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统部门表';

-- ------------------------------------------------------------
-- sys_organization | 系统组织（公司）表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sys_organization;
CREATE TABLE IF NOT EXISTS sys_organization (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    user_id BIGINT NULL COMMENT '法定代表人ID（SysUser表主键ID）',
    organization_name VARCHAR(255) NULL COMMENT '组织名称',
    organization_establishment_time DATETIME NULL COMMENT '组织成立时间',
    organization_code VARCHAR(255) NULL COMMENT '组织编码（统一社会信用代码）',
    organization_phone_number VARCHAR(255) NULL COMMENT '组织电话号码',
    organization_email VARCHAR(255) NULL COMMENT '组织邮箱',
    organization_website VARCHAR(255) NULL COMMENT '组织官网',
    organization_address VARCHAR(255) NULL COMMENT '组织地址',
    organization_introduction VARCHAR(255) NULL COMMENT '组织简介',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统组织（公司）表';

-- ------------------------------------------------------------
-- sys_user | 系统用户表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL COMMENT '主键ID',
    create_time DATETIME NULL COMMENT '创建时间',
    create_user_id BIGINT NULL COMMENT '创建者ID',
    update_time DATETIME NULL COMMENT '修改时间',
    update_user_id BIGINT NULL COMMENT '修改者ID',
    data_user_id BIGINT NULL COMMENT '数据所属人ID',
    data_dept_id BIGINT NULL COMMENT '数据所属部门ID',
    is_delete INT NULL COMMENT '逻辑删除（1：存在。-1：逻辑删除）',
    dept_id BIGINT NULL COMMENT '所属部门ID（SysDept表主键ID）',
    user_name VARCHAR(255) NULL COMMENT '用户名称',
    login_name VARCHAR(255) NULL COMMENT '登录名',
    password VARCHAR(255) NULL COMMENT '密码',
    phone_number VARCHAR(255) NULL COMMENT '电话号码',
    email VARCHAR(255) NULL COMMENT '邮箱',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统用户表';
